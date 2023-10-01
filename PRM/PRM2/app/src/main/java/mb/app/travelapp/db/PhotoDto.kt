package mb.app.travelapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photo")
data class PhotoDto (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val note: String,
    @ColumnInfo
    val imgLoc: String,
    @ColumnInfo
    val lon: Double,
    @ColumnInfo
    val lat: Double

)