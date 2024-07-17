package it.unibo.android.lab.newsapp.repository

import it.unibo.android.lab.newsapp.api.RetrofitInstance
import it.unibo.android.lab.newsapp.db.NewsBodyDatabase
import it.unibo.android.lab.newsapp.models.NewsBody

class NewsRepository(val db: NewsBodyDatabase) {
    suspend fun getNews() =
        RetrofitInstance.apiClient.getMarketNews()

    suspend fun upsert(article: NewsBody) {
        val existingArticle = db.getArticleDao().getArticleById(article.url)
        if (existingArticle == null) {
            // Article doesn't exist, insert it
            db.getArticleDao().upsert(article)
        }
    }

    fun getWatchLaterNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: NewsBody) = db.getArticleDao().deleteArticle(article)
}