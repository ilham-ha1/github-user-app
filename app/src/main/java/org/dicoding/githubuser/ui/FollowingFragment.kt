package org.dicoding.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.dicoding.githubuser.adapter.FollowingAdapter
import org.dicoding.githubuser.databinding.FragmentFollowringBinding
import org.dicoding.githubuser.viewModels.DetailViewModel

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowringBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_DETAIL_USER).toString()

        _binding = FragmentFollowringBinding.bind(view)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollowing.layoutManager = layoutManager

        detailViewModel.listFollowing.observe(viewLifecycleOwner){ items->
            val adapter = FollowingAdapter(items)
            binding.rvUserFollowing.adapter = adapter
        }

        if (username != null) {
            detailViewModel.getFollowing(username!!)
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}