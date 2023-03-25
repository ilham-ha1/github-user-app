package org.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.dicoding.githubuser.R
import org.dicoding.githubuser.adapter.SectionsPagerAdapter
import org.dicoding.githubuser.databinding.ActivityDetailBinding
import org.dicoding.githubuser.viewModels.MainViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel>()

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getStringExtra(EXTRA_DETAIL_USER)
        val bundle = Bundle()
        bundle.putString(EXTRA_DETAIL_USER, user)

        if (user != null) {
            mainViewModel.getDetailUser(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.detailUser.observe(this){
            binding.profileImage.let {
                Glide.with(binding.root.context)
                    .load(mainViewModel.detailUser.value?.avatarUrl)
                    .into(binding.profileImage)
            }
            binding.tvName.text = mainViewModel.detailUser.value?.name.toString()
            binding.tvUsername.text = mainViewModel.detailUser.value?.login.toString()
            binding.tvFollowers.text = "${mainViewModel.detailUser.value?.followers.toString()} Followers"
            binding.tvFollowing.text = "${mainViewModel.detailUser.value?.following.toString()} Following"
        }

        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter =  SectionsPagerAdapter(this,bundle)

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when(position){
                0 ->{
                    tab.text = resources.getString(TAB_TITLES[0])
                    tab.icon = ContextCompat.getDrawable(baseContext,TAB_ICON[0])
                }
                1 ->{
                    tab.text = resources.getString(TAB_TITLES[1])
                    tab.icon = ContextCompat.getDrawable(baseContext,TAB_ICON[1])
                }
            }

        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_user_menu,menu)
        return true
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