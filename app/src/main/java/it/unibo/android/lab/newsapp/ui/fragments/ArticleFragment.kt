package it.unibo.android.lab.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.databinding.FragmentArticleBinding
import it.unibo.android.lab.newsapp.ui.NewsActivity
import it.unibo.android.lab.newsapp.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article){

    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        val article = args.article


        binding.webView.apply {
            webViewClient = webViewClient
        }
    }

}