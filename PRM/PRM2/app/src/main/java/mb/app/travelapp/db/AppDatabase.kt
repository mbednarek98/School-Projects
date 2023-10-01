package mb.app.travelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
        entities = [PhotoDto::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val photo: PhotoDao
    companion object {
        fun open(context: Context) = Room.databaseBuilder(
                context, AppDatabase::class.java, "photodb"
        ).build()
    }
}