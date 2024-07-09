package it.unibo.android.lab.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.MarketNewsAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentHeadlineBinding
import it.unibo.android.lab.newsapp.MainActivity
import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel
import it.unibo.android.lab.newsapp.util.Resource

class HeadlineFragment : Fragment(R.layout.fragment_headline) {

    private val TAG = "HeadlineFragment"

    private lateinit var newsViewModel: NewsViewModel
    private lateinit var marketNewsAdapter: MarketNewsAdapter
    private lateinit var retryButton: Button
    private lateinit var errorText: TextView
    private lateinit var itemHeadlinesError: CardView
    private lateinit var binding: FragmentHeadlineBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlineBinding.bind(view)


        itemHeadlinesError = view.findViewById(R.id.itemHeadlinesError)

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val headlineView: View = inflater.inflate(R.layout.item_error, null)

        retryButton = headlineView.findViewById(R.id.retryButton)
        errorText = headlineView.findViewById(R.id.errorText)

        newsViewModel = (activity as MainActivity).newsViewModel
        setupHeadlinesRecycler()

        marketNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            Log.d(TAG, "Navigate to article with bundle content: $bundle")
            findNavController().navigate(R.id.action_headlinesFragment_to_articleFragment, bundle)
        }

        newsViewModel.newsResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->
                        marketNewsAdapter.differ.submitList(newsResponse.body.toList())
                    }
                }
                is Resource.Error<*> -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Sorry error: $message", Toast.LENGTH_LONG).show()
                        showErrorMessage(message)
                    }
                }
                is Resource.Loading<*> -> {
                    showProgressBar()
                }
            }
        }

        retryButton.setOnClickListener {
            newsViewModel.getMarketNews()
        }
    }

    private var isError = false
    private var isLoading = false

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemHeadlinesError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemHeadlinesError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }
    private fun setupHeadlinesRecycler() {
        marketNewsAdapter = MarketNewsAdapter()
        binding.recyclerHeadlines.apply {
            adapter = marketNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}