package it.unibo.android.lab.newsapp.models

data class QuotesResponse(
    val body: MutableList<QuotesBody>,
    val meta: QuotesMeta
)