package it.unibo.android.lab.newsapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import it.unibo.android.lab.newsapp.R
import it.unibo.android.lab.newsapp.databinding.ActivityNewsBinding
import it.unibo.android.lab.newsapp.db.ArticleDatabase
import it.unibo.android.lab.newsapp.repository.NewsRepository

class NewsActivity : AppCompatActivity() {

  lateinit var newsViewModel: NewsViewModel
  lateinit var binding: ActivityNewsBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNewsBinding.inflate(layoutInflater)
    enableEdgeToEdge()
    setContentView(binding.root)
    /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets }*/


    // Creating an instance of newsRep. and providing it w an article database instance
    val newsRepository = NewsRepository(ArticleDatabase(this))
    val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
    newsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
    val navController = navHostFragment.navController
    binding.bottomNavigationView.setupWithNavController(navController)
  }
}