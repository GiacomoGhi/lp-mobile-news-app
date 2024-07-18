package it.unibo.android.lab.newsapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import it.unibo.android.lab.newsapp.MainActivity
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.adapters.QuotesAdapter
import it.unibo.android.lab.newsapp.databinding.FragmentQuotesBinding
import it.unibo.android.lab.newsapp.ui.viewmodels.QuotesViewModel
import it.unibo.android.lab.newsapp.util.Resource

class QuotesFragment : Fragment(R.layout.fragment_quotes) {

    private val TAG = "QuotesFragment"

    private lateinit var quotesViewModel: QuotesViewModel
    private lateinit var quotesAdapter: QuotesAdapter
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
        setupQuotesRecycler()

        quotesViewModel.response.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { newsResponse ->
                        quotesAdapter.submitList(newsResponse.body.toList())
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
            quotesViewModel.getData()
        }

        setupSearch()
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

    private fun setupQuotesRecycler() {
        quotesAdapter = QuotesAdapter()
        binding.recyclerQuotes.apply {
            adapter = quotesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupSearch() {
        val searchEditText: EditText = binding.searchEdit
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                quotesAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
