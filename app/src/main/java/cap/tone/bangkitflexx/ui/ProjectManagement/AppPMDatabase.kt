package cap.tone.bangkitflexx.ui.ProjectManagement

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cap.tone.bangkitflexx.Model.PMModel
import cap.tone.bangkitflexx.database.PMDao

@Database(entities = [PMModel::class], version = 1)
abstract class AppPMDatabase : RoomDatabase() {
    abstract fun PMDao(): PMDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppPMDatabase? = null

        fun getDatabase(context: Context): AppPMDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppPMDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}