package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.databinding.FragmentArticleBinding
import it.unibo.android.lab.newsapp.MainActivity
import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article){

    private lateinit var newsViewModel: NewsViewModel

    // Here access the article that is passed as argument to this fragment
    // check argument tag in news_nav_graph.xml
    private val args: ArticleFragmentArgs by navArgs() // built in safe args method to access fragment arguments

    private lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewModel
        val article = args.article

        binding.webView.apply {
            // webViewClient handles various events in the webView such as when a new URL is about to be loaded
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.backArrow.setOnClickListener{
            findNavController().navigate(R.id.action_articleFragment_to_headlinesFragment)

        }

        binding.fab.setOnClickListener {
            newsViewModel.addToWatchLater(article)
            Snackbar.make(view, "Added to watch later!", Snackbar.LENGTH_SHORT).show()
        }
    }

}