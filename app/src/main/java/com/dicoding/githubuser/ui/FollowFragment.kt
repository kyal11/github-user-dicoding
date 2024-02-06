package com.dicoding.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.adapter.FollowAdapter
import com.dicoding.githubuser.databinding.FragmentFollowersFollowingBinding
import com.dicoding.githubuser.viewmodel.FollowViewModel
import com.google.android.material.snackbar.Snackbar


class FollowFragment : Fragment() {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FollowViewModel>()
    private val adapter = FollowAdapter()
    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }
        showRecyclerView()
        observeLoadingState()

        viewModel.snackbarText.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showRecyclerView() {
        if (position == 1) {
            viewModel.followersUser(UserDetailActivity.username)
            viewModel.followersUser.observe(viewLifecycleOwner) { followersUser ->
                adapter.submitList(followersUser)
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.setHasFixedSize(true)
                binding.rvUser.adapter = adapter
            }
        } else {
            viewModel.followingUser(UserDetailActivity.username)
            viewModel.followingUser.observe(viewLifecycleOwner) { followingUser ->
                adapter.submitList(followingUser)
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.setHasFixedSize(true)
                binding.rvUser.adapter = adapter
            }
        }
    }
    private fun observeLoadingState() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    companion object {
        const val ARG_USERNAME = "arg_username"
        const val ARG_POSITION = "arg_position"

        @JvmStatic
        fun newInstance(position: Int, username: String): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }
}