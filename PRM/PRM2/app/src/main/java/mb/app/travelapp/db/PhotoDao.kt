package mb.app.travelapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Insert
    fun insert(photo: PhotoDto) : Long

    @Query("SELECT * FROM photo;")
    fun selectAll(): List<PhotoDto>

    @Query("SELECT * FROM photo WHERE id = :id;")
    fun selectByID(id: Int): PhotoDto

    @Query("UPDATE photo SET note = :note WHERE id = :id;")
    fun update(note : String, id: Int) : Int

    @Query("DELETE FROM photo")
    fun delete()
}