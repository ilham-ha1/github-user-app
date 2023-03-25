package org.dicoding.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import org.dicoding.githubuser.adapter.FollowerAdapter
import org.dicoding.githubuser.databinding.FragmentFollowerBinding
import org.dicoding.githubuser.viewModels.DetailViewModel


class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel by viewModels<DetailViewModel>()
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(DetailActivity.EXTRA_DETAIL_USER).toString()

        _binding = FragmentFollowerBinding.bind(view)

        setupRecyclerView(username)
    }


    private fun setupRecyclerView(username:String?) {
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvUserFollower.layoutManager = layoutManager

        detailViewModel.listFollower.observe(viewLifecycleOwner){ items->
            val adapter = FollowerAdapter(items)
            binding.rvUserFollower.adapter = adapter
        }

        if (username != null) {
            detailViewModel.getFollower(username)
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}