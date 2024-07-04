package it.unibo.android.lab.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import it.unibo.android.lab.newsapp.models.NewsBody

@Database(
    entities = [NewsBody::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class NewsBodyDatabase: RoomDatabase() {

    abstract fun getArticleDao(): NewsBodyDAO

    companion object {
        @Volatile
        private var instance: NewsBodyDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsBodyDatabase::class.java,
                "newsBody_db.db" // Corrected the syntax error here
            ).build()
    }
}