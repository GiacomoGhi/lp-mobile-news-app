package it.unibo.android.lab.newsapp.models

data class NewsResponse(
    val body: MutableList<NewsBody>,
    val meta: NewsMeta
)