import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.plm.junglejumble.database.dao.UserDao
import com.plm.junglejumble.database.models.User
import androidx.room.Room
import com.plm.junglejumble.database.dao.ScoreDao
import com.plm.junglejumble.database.models.Score

@Database(entities = [User::class, Score::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
