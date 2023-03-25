package org.dicoding.githubuser.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.dicoding.githubuser.ui.FollowerFragment
import org.dicoding.githubuser.ui.FollowingFragment


class SectionsPagerAdapter(activity: AppCompatActivity, data:Bundle):FragmentStateAdapter(activity) {
    private var fragmentBundle:Bundle
    init{
        fragmentBundle = data
    }
    override fun getItemCount(): Int {
       return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}

