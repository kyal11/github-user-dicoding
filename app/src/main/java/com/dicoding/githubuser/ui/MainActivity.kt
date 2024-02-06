package com.dicoding.githubuser.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.adapter.UsersAdapter
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAdapterUser()
        observeViewModel()

        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { _, actionId, event ->
                searchView.hide()
                viewModel.searchUser(searchView.text.toString())
                false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.searchList.observe(this) { listUser ->
            if (listUser.isNotEmpty()) {
                binding.rvUser.visibility = View.VISIBLE
                adapter.submitList(listUser)
            } else {
                binding.rvUser.visibility = View.GONE
                val message = "User Not Found!"
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.snackbarText.observe(this) { it.getContentIfNotHandled()?.let { snackBarText ->
            Snackbar.make(
                window.decorView.rootView,
                snackBarText,
                Snackbar.LENGTH_SHORT
            ).show()
        }
        }

        viewModel.isLoading.observe(this, this::showLoading)
    }

    private fun showAdapterUser() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUser.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUser.layoutManager = LinearLayoutManager(this)
        }
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                selectUser(data)
            }
        })
    }

    private fun selectUser(user: ItemsItem) {
        val detailActivity = Intent(this, UserDetailActivity::class.java)
        detailActivity.putExtra(UserDetailActivity.DETAIL_USER, user.login)
        startActivity(detailActivity)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
