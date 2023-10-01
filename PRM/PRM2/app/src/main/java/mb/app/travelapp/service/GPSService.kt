package mb.app.travelapp.service


import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.*
import mb.app.travelapp.ImgActivity
import mb.app.travelapp.R
import mb.app.travelapp.Shared
import mb.app.travelapp.Shared.ADDRESS
import mb.app.travelapp.Shared.COUNTRY
import mb.app.travelapp.Shared.FASTEST_INTERVAL
import mb.app.travelapp.Shared.LAT
import mb.app.travelapp.Shared.LOCATION
import mb.app.travelapp.Shared.LON
import mb.app.travelapp.Shared.SERVICE_CHANNEL_ID
import mb.app.travelapp.Shared.SERVICE_CHANNEL_NAME
import mb.app.travelapp.Shared.UPDATE_INTERVAL
import mb.app.travelapp.databinding.ActivitySettingsBinding
import mb.app.travelapp.db.PhotoDto
import java.io.File
import java.util.*

class GPSService: Service() {
    private val pref by lazy { getSharedPreferences("SaveData",Context.MODE_PRIVATE) }
    var photoList : List<PhotoDto>? = null
    lateinit var notifList : List<PhotoDto>
    lateinit var locationClient: FusedLocationProviderClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        notifList = listOf()
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        startForeground(1, createNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        getGPSTrack()
        return START_STICKY
    }

    private fun getGPSTrack(){
        val locationRequest = LocationRequest()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL
            fastestInterval = FASTEST_INTERVAL
        }

        if(!checkLocationPermission()){
            stopSelf()
            return
        }

        val geocode = Geocoder(this, Locale.getDefault())
        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                getFromDB().start()
                Thread.sleep(100)
                locationResult?.locations?.get(0)?.let { setLocation(geocode, it) }
                for(photo in photoList!!){
                    if(getDistance(photo) <= pref.getInt("DISTANCE",100)) {
                        if (!notifList.contains(photo)) {
                            notifList += photo
                            with(NotificationManagerCompat.from(this@GPSService)) { notify(photo.id,  buildBaseNotifier(photo)) }
                        }
                    }
                }
            }
        }
        
        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun createNotification(): Notification {
        val channel = NotificationChannel(SERVICE_CHANNEL_ID, SERVICE_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        return NotificationCompat.Builder(this, SERVICE_CHANNEL_ID)
                .setContentTitle("TravelApp")
                .setContentText("Aplikacja jest w trakcie dzialania")
                .setSmallIcon(R.drawable.travel)
                .build()
    }

    private fun setLocation(geocode: Geocoder, loc : Location){
        LOCATION = loc
        LON = LOCATION!!.longitude
        LAT = LOCATION!!.latitude
        val addr = geocode.getFromLocation(LOCATION!!.latitude, LOCATION!!.longitude, 1)
        ADDRESS = addr[0].locality
        if(ADDRESS == null) ADDRESS = addr[0].adminArea
        COUNTRY = addr[0].countryName
        Log.d("Location", "$ADDRESS $COUNTRY")
    }

    private fun getFromDB() = Thread{
        val data = Shared.db?.photo?.selectAll()
        photoList = data?.map { PhotoDto(it.id, it.note, it.imgLoc, it.lon, it.lat) }
        Log.d("DBService",photoList.toString())
    }

    private fun getDistance(photo : PhotoDto) : Float {
        return  Location("img").apply {
            latitude = photo.lat
            longitude = photo.lon
        }.distanceTo(LOCATION)/1000
    }
    
    private fun buildBaseNotifier(photo : PhotoDto) : Notification {
        return NotificationCompat.Builder(this@GPSService, SERVICE_CHANNEL_ID)
                .setContentTitle("$ADDRESS, $COUNTRY")
                .setContentText(photo.note)
                .setLargeIcon(MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(File(photo.imgLoc))))
                .setSmallIcon(R.drawable.camera)
                .setContentIntent(PendingIntent.getActivity(this@GPSService, 0, Intent(this@GPSService, ImgActivity::class.java).putExtra("idPhoto", photo.id), PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
    }


}