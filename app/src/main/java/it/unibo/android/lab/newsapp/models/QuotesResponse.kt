package it.unibo.android.lab.newsapp.models

data class QuotesResponse(
    val quotesBody: MutableList<QuotesBody>,
    val quotesMeta: QuotesMeta
)