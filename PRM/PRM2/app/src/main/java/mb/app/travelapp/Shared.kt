package mb.app.travelapp

import android.location.Location
import mb.app.travelapp.db.AppDatabase

object Shared {
    var db: AppDatabase? = null;
    const val STRING_DATE_FORMAT = "yyyyMMdd_HHmmss"
    const val FILE_PREFIX = "JPEG"
    const val FILE_SUFFIX = ".jpg"
    const val AUTHORITY_NAME = "mb.app.travelapp.fileprovider"
    const val UPDATE_INTERVAL = 10000L
    const val FASTEST_INTERVAL = 2000L
    const val SERVICE_CHANNEL_ID = "travelapp_channel_01"
    const val SERVICE_CHANNEL_NAME = "TravelApp channel"
    var LOCATION: Location? = null
    var ADDRESS: String? = null
    var COUNTRY: String? = null
    var LON: Double? = null
    var LAT: Double? = null
}