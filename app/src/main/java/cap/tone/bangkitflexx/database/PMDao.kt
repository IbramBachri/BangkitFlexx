package cap.tone.bangkitflexx.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cap.tone.bangkitflexx.model.PMModel

@Dao
interface PMDao {
    @Insert()
    suspend fun insertTask(todoModel: PMModel):Long

    @Query("Select * from PMModel where isFinished == 0")
    fun getTask(): LiveData<List<PMModel>>

    @Query("Update PMModel Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from PMModel where id=:uid")
    fun deleteTask(uid:Long)
}