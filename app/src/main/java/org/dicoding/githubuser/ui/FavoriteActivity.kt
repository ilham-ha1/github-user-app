package org.dicoding.githubuser.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.dicoding.githubuser.adapter.FavoriteAdapter
import org.dicoding.githubuser.databinding.ActivityFavoriteBinding
import org.dicoding.githubuser.factory.FavoriteUserViewModelFactory
import org.dicoding.githubuser.viewModels.FavoriteUserAddViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteUserViewModel by viewModels<FavoriteUserAddViewModel>{ FavoriteUserViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Favorite User"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager

        favoriteUserViewModel.listItem.observe(this){items->
            val adapter = FavoriteAdapter(items)
            binding.rvUser.adapter = adapter
        }

        favoriteUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        favoriteUserViewModel.getFavorite()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}