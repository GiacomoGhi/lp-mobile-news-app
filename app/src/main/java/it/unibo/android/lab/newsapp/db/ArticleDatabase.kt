package it.unibo.android.lab.newsapp.db

import androidx.room.Database
import androidx.room.TypeConverters
import it.unibo.android.lab.newsapp.models.Article

@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase {
}