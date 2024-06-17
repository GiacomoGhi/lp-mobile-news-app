package it.unibo.android.lab.newsapp.repository

import it.unibo.android.lab.newsapp.api.RetrofitInstance
import it.unibo.android.lab.newsapp.db.ArticleDatabase
import it.unibo.android.lab.newsapp.models.Article

class NewsRepository(val db: ArticleDatabase) {
    suspend fun getNews() =
        RetrofitInstance.apiClient.getMarketNews()

    suspend fun getQuotes(ticker: List<String>?) =
        RetrofitInstance.apiClient.getMarketQuotes(ticker)

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}