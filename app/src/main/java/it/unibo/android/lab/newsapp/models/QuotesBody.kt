package it.unibo.android.lab.newsapp.models

import java.io.Serializable

data class QuotesBody(
    val ask: Double, // prezzo a cui si vende (prezzo a sinistra)
    val bid: Double, // prezzo a destra
    val currency: String, // Valuta attuale (non nello screen, al momento omissibile, magari metterla di fianco all'ask) (quasi sempre USD)
    val displayName: String, // Apple
    val longName: String, // Apple inc ecc..
    val symbol: String, // APL
    val regularMarketChangePercent: Double //percentuale che sta sopra al nome (di variazione del prezzo in quel giorno)
): Serializable