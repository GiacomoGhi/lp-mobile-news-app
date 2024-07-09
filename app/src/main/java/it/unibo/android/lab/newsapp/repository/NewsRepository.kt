package it.unibo.android.lab.newsapp.repository

import it.unibo.android.lab.newsapp.api.RetrofitInstance
import it.unibo.android.lab.newsapp.db.NewsBodyDatabase
import it.unibo.android.lab.newsapp.models.NewsBody

class NewsRepository(val db: NewsBodyDatabase) {
    suspend fun getNews() =
        RetrofitInstance.apiClient.getMarketNews()

    suspend fun upsert(article: NewsBody) = db.getArticleDao().upsert(article)

    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: NewsBody) = db.getArticleDao().deleteArticle(article)
}