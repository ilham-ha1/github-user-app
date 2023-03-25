package org.dicoding.githubuser.ui

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.dicoding.githubuser.R
import androidx.appcompat.widget.SearchView
import org.dicoding.githubuser.adapter.UserAdapter
import org.dicoding.githubuser.databinding.ActivityMainBinding
import org.dicoding.githubuser.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        mainViewModel.listItem.observe(this){items->
            val adapter = UserAdapter(items)
            binding.rvUser.adapter = adapter
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getSearchItem("ilham")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.seacrh_user_menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getSearchItem(query)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                mainViewModel.getSearchItem(newText)
                return true
            }
        })

        return true
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}