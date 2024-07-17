package it.unibo.android.lab.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.unibo.android.lab.newsapp.models.NewsBody

@Dao
interface NewsBodyDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: NewsBody): Long

    @Query("SELECT * FROM newsBody")
    fun getAllArticles(): LiveData<List<NewsBody>>
    @Query("SELECT * FROM newsBody")
    fun getArticlesSync(): List<NewsBody>

    @Query("SELECT * FROM newsBody WHERE url = :url")
    suspend fun getArticleById(url: String): NewsBody?

    @Delete
    suspend fun deleteArticle(article: NewsBody)
}