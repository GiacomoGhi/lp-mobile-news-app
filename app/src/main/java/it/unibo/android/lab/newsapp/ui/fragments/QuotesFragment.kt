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
import it.unibo.android.lab.newsapp.adapters.QuotesAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentQuotesBinding
import it.unibo.android.lab.newsapp.models.QuotesResponse
import it.unibo.android.lab.newsapp.ui.viewmodels.NewsViewModel
import it.unibo.android.lab.newsapp.ui.viewmodels.QuotesViewModel
import it.unibo.android.lab.newsapp.util.Resource

class QuotesFragment : Fragment(R.layout.fragment_quotes) {

    private val TAG = "QuotesFragment"

    private lateinit var quotesViewModel: QuotesViewModel
    //private lateinit var marketNewsAdapter: MarketNewsAdapter
    private lateinit var retryButton: Button
    private lateinit var errorText: TextView
    private lateinit var itemQuotesError: CardView
    private lateinit var binding: FragmentQuotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuotesBinding.bind(view)

        itemQuotesError = view.findViewById(R.id.itemQuotesError)

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val quotesView: View = inflater.inflate(R.layout.item_error, null)

        retryButton = quotesView.findViewById(R.id.retryButton)
        errorText = quotesView.findViewById(R.id.errorText)

        quotesViewModel = (activity as MainActivity).quotesViewModel
        //setupHeadlinesRecycler()

        quotesViewModel.response.observe(viewLifecycleOwner) { response ->
            when(response){
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->

                        //Qui passo i dati a QuotesAdapter
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
        itemQuotesError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemQuotesError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }


}