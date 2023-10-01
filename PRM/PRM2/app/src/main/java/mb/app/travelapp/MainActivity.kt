package mb.app.travelapp


import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import mb.app.travelapp.Shared.AUTHORITY_NAME
import mb.app.travelapp.Shared.FILE_PREFIX
import mb.app.travelapp.Shared.FILE_SUFFIX
import mb.app.travelapp.Shared.STRING_DATE_FORMAT
import mb.app.travelapp.adapter.PhotoAdapter
import mb.app.travelapp.databinding.ActivityMainBinding
import mb.app.travelapp.db.AppDatabase
import mb.app.travelapp.db.PhotoDto
import mb.app.travelapp.service.GPSService
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val REQUEST_IMAGE_CAPTURE = 42

class MainActivity : AppCompatActivity() {
    var list :List<PhotoDto>? = null
    var photoURI: Uri? = null
    lateinit var currentPhotoPath: String
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Shared.db = AppDatabase.open(applicationContext)
        setupVH()
        startLocationService()
        binding.photoWidget.setOnClickListener { dispatchTakePictureIntent() }
    }

    override fun onResume() {
        super.onResume()
        setupVH()
    }


    private fun setupPhotoList(){
        binding.photoList.apply {
            adapter = list?.let { PhotoAdapter(it, this@MainActivity) }
            layoutManager = GridLayoutManager(context, 4)
        }
    }

    private fun setupVH(){
        getFromDB().start()
        Thread.sleep(100)
        setupPhotoList()
    }

    private fun getFromDB() = Thread{
        val data = Shared.db?.photo?.selectAll()
        list = data?.map {
            Log.d("DB", it.imgLoc)
            PhotoDto(it.id, it.note, it.imgLoc, it.lon, it.lat)
        }
        Log.d("DBMainActivity",list.toString())

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                this.startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isLocationServiceRunning(): Boolean {
        val manager: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) if ("mb.app.travelapp.services.GPSService" == service.service.className) return true
        return false
    }


    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val serviceIntent = Intent(this, GPSService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(serviceIntent)
            else startService(serviceIntent)
        }
    }



    /** Metody poswiecone dzialaniu kamery i jego zapisu w pamieci
     * https://developer.android.com/training/camera/photobasics
     */



    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat(STRING_DATE_FORMAT).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "${FILE_PREFIX}_${timeStamp}_",
                FILE_SUFFIX,
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(this.packageManager).also {
                val photoFile: File? = try { createImageFile() } catch (ex: IOException) {
                    Log.e("error",ex.toString())
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            AUTHORITY_NAME,
                            it
                    )
                    this.photoURI = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (this.photoURI != null) {
                val intent = Intent(this, ImgActivity::class.java)
                intent.putExtra("img_uri", photoURI)
                startActivity(intent)
                this.photoURI = null
            } else Toast.makeText(this, "Can't get photo URI", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}