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
import com.dicoding.githubuser.viewmodel.MainViewModel

class FollowFragment : Fragment() {
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val adapter = FollowAdapter()
    private var position: Int = 0
    private var username: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }
//        if (position == 1) {
//            binding.testUsername.text = "Get Follower $username"
//        } else {
//            binding.testUsername.text = "Get Following $username"
//        }
        showRecyclerView(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showRecyclerView(position: Int) {
        if (position == 1) {
            viewModel.followersUser(ARG_USERNAME)
            viewModel.followersUser.observe(viewLifecycleOwner) { followersUser ->
                adapter.submitList(followersUser)
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.setHasFixedSize(true)
                binding.rvUser.adapter = adapter
            }
        } else {
            viewModel.followingUser(ARG_USERNAME)
            viewModel.followingUser.observe(viewLifecycleOwner) { followingUser ->
                adapter.submitList(followingUser)
                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.setHasFixedSize(true)
                binding.rvUser.adapter = adapter
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