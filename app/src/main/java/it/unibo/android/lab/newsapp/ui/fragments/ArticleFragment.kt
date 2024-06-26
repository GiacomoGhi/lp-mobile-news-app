package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.databinding.FragmentArticleBinding
import it.unibo.android.lab.newsapp.ui.NewsActivity
import it.unibo.android.lab.newsapp.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article){

    lateinit var newsViewModel: NewsViewModel

    // Here access the article that is passed as argument to this fragment
    // check argument tag in news_nav_graph.xml
    val args: ArticleFragmentArgs by navArgs() // built in safe args method to access fragment arguments

    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            // webViewClient handles various events in the webView such as when a new URL is about to be loaded
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            newsViewModel.addToFavourites(article)
            // Snackbar is used to inform the user that the article has been added to fav
            Snackbar.make(view, "Added to favourites!", Snackbar.LENGTH_SHORT).show()
        }
    }

}