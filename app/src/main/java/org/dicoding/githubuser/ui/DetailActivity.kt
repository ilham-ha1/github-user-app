package org.dicoding.githubuser.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.dicoding.githubuser.R
import org.dicoding.githubuser.adapter.SectionsPagerAdapter
import org.dicoding.githubuser.databinding.ActivityDetailBinding
import org.dicoding.githubuser.factory.MainViewModelFactory
import org.dicoding.githubuser.preferences.SettingPreferences
import org.dicoding.githubuser.viewModels.MainViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            application,
            SettingPreferences.getInstance(dataStore)
        )
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        private val TAB_ICON = intArrayOf(
            R.drawable.ic_baseline_people_24,
            R.drawable.ic_baseline_people_24
        )
        const val EXTRA_DETAIL_USER = "extra_detail_user"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_IMG = "extra_img"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail User"
        val user = intent.getStringExtra(EXTRA_DETAIL_USER)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val img = intent.getStringExtra(EXTRA_IMG)

        val bundle = Bundle()
        bundle.putString(EXTRA_DETAIL_USER, user)

        if (user != null) {
            mainViewModel.getDetailUser(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var selected = false
        CoroutineScope(Dispatchers.IO).launch {
            val counter = mainViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (counter != null) {
                    if (counter > 0) {
                        binding.toggleButton.isChecked = true
                        selected = true
                    } else {
                        binding.toggleButton.isChecked = false
                        selected = false
                    }
                } else {
                    Log.d("counter detail act", "counter is null")
                }
            }
        }

        mainViewModel.detailUser.observe(this) {
            binding.profileImage.let {
                Glide.with(binding.root.context)
                    .load(mainViewModel.detailUser.value?.avatarUrl)
                    .into(binding.profileImage)
            }
            binding.tvName.text = mainViewModel.detailUser.value?.name.toString()
            binding.tvUsername.text = mainViewModel.detailUser.value?.login.toString()
            binding.tvFollowers.text =
                "${mainViewModel.detailUser.value?.followers.toString()} Followers"
            binding.tvFollowing.text =
                "${mainViewModel.detailUser.value?.following.toString()} Following"
        }


        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = SectionsPagerAdapter(this, bundle)

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = resources.getString(TAB_TITLES[0])
                    tab.icon = ContextCompat.getDrawable(baseContext, TAB_ICON[0])
                }
                1 -> {
                    tab.text = resources.getString(TAB_TITLES[1])
                    tab.icon = ContextCompat.getDrawable(baseContext, TAB_ICON[1])
                }
            }

        }.attach()

        supportActionBar?.elevation = 0f

        binding.toggleButton.setOnClickListener {
            selected = !selected
            if (selected) {
                if (user != null && img != null) {
                    mainViewModel.addFavorite(user, img, id)
                }
            } else {
                mainViewModel.removeFavorite(id)
            }
            binding.toggleButton.isChecked = selected
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
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