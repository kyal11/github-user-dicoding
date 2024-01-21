package com.dicoding.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.UsersAdapter
import com.dicoding.githubuser.databinding.ActivityMainBinding
import com.dicoding.githubuser.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,  ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)

            searchView.editText.setOnEditorActionListener { _, actionId, event ->
              searchView.hide()
              viewModel.searchUser(searchView.text.toString())
                false
            }
        }
        showListUser()

    }

    fun showListUser() {
        viewModel.searchUser("rizky")
        viewModel.searchList.observe(this) { listUser ->
            val adapter = UsersAdapter(ArrayList(listUser))
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                binding.rvUser.layoutManager = GridLayoutManager(this, 2)
            } else {
                binding.rvUser.layoutManager = LinearLayoutManager(this)
            }
            binding.rvUser.setHasFixedSize(true)
            binding.rvUser.adapter = adapter
        }
    }

}