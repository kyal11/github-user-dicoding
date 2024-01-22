package com.dicoding.githubuser.ui


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.adapter.UsersAdapter
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        showListUser()
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { _, actionId, event ->
                searchView.hide()
                viewModel.searchUser(searchView.text.toString())
                false
            }
        }
        viewModel.isLoading.observe(this, this::showLoading)
    }

    private fun showListUser() {
        viewModel.searchUser("rizky")
        viewModel.searchList.observe(this) { listUser ->
            adapter.submitList(listUser)
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.rvUser.layoutManager = GridLayoutManager(this, 2)
            } else {
                binding.rvUser.layoutManager = LinearLayoutManager(this)
            }
            binding.rvUser.setHasFixedSize(true)
            binding.rvUser.adapter = adapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}