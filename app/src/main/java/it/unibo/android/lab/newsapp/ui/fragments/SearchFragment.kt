package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.navArgs
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.NewsAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentArticleBinding
import it.unibo.android.lab.newsapp.ui.NewsActivity
import it.unibo.android.lab.newsapp.ui.NewsViewModel

class SearchFragment  {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var retryButton: Button
    lateinit var errorText: TextView
    lateinit var itemSearchError: CardView

}