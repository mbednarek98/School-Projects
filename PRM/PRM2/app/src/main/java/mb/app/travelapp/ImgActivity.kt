package mb.app.travelapp

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import mb.app.travelapp.Shared.ADDRESS
import mb.app.travelapp.Shared.COUNTRY
import mb.app.travelapp.Shared.LAT
import mb.app.travelapp.Shared.LON
import mb.app.travelapp.Shared.db
import mb.app.travelapp.databinding.ActivityImgBinding
import mb.app.travelapp.db.PhotoDto
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class ImgActivity : AppCompatActivity() {
    var photo : PhotoDto? = null
    private val pref by lazy { getSharedPreferences("SaveData",Context.MODE_PRIVATE) }
    private val binding by lazy { ActivityImgBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val uri = getUri(intent)
        setActivityByUri(uri)
        }


    private fun setActivityByUri(uri : Uri?){
        val bitmap: Bitmap?
        if(uri != null) {
            bitmap = paintPhoto(uri)
            binding.photoView.setImageBitmap(bitmap)
            binding.addWidget.setOnClickListener {
                val photodb = PhotoDto(
                        note = binding.noteText.text.toString(),
                        imgLoc = bitmapToFile(bitmap!!).toString(),
                        lat = LAT!!,
                        lon = LON!!
                )
                saveToDB(photodb).start()
                this.finish()
            }
        }
        else {
            val id = intent.extras?.get("idPhoto") as Int
            getFromDBID(id).start()
            Thread.sleep(100)
            binding.noteText.setText(this.photo?.note)
            binding.photoView.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(File(photo?.imgLoc.toString()))))
            binding.addWidget.setOnClickListener {
                updateDB(binding.noteText.text.toString(), id).start()
                this.finish()
            }
        }
    }

    private fun getUri(intent: Intent): Uri? {
        val extras = intent.extras
        var uri: Uri? = null
        if (extras != null) uri = intent.extras?.get("img_uri") as Uri?
        return uri
    }

    private fun paintPhoto(uri : Uri): Bitmap? {
        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val newImageBitmap = Bitmap.createBitmap(imageBitmap.width, imageBitmap.height, imageBitmap.config)
        val canvas = Canvas(newImageBitmap)
        val paint = Paint().apply {
            color = getPrefColor()
            textSize = pref.getInt("SIZE",0).toFloat()
            style = Paint.Style.FILL
        }
        canvas.drawPaint(paint)
        canvas.drawBitmap(imageBitmap, 0F, 0F, null)
        canvas.drawText("$ADDRESS, $COUNTRY", 10F, 50F, paint)
        canvas.drawText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()), 10F, 125F, paint)
        return newImageBitmap
    }

    private fun getPrefColor(): Int {
        return when (pref.getString("COLOR","Color.WHITE")) {
            "Color.WHITE" -> Color.WHITE
            "Color.BLACK" -> Color.BLACK
            "Color.YELLOW" -> Color.YELLOW
            "Color.GREEN" -> Color.GREEN
            "Color.BLUE" -> Color.BLUE
            "Color.RED" -> Color.RED
            else -> 0
        }
    }

    private fun bitmapToFile(bitmap:Bitmap): Uri {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = File(file,"${UUID.randomUUID()}.jpg")
        try{
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    private fun saveToDB(photoDB : PhotoDto) = Thread{
        db?.photo?.insert(photoDB)
    }


    private fun updateDB(note : String, id: Int) = Thread{
        db?.photo?.update(note,id)
    }

    private fun getFromDBID(id : Int) = Thread{
        photo = db?.photo?.selectByID(id)

    }



}

