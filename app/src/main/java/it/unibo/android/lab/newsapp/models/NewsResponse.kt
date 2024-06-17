package it.unibo.android.lab.newsapp.models

data class NewsResponse(
    val newsBody: MutableList<NewsBody>,
    val newsMeta: NewsMeta
)