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
    //private lateinit var itemQuotesError: CardView
    private lateinit var binding: FragmentQuotesBinding

}