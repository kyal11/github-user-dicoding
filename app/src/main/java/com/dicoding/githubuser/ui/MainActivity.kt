package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
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

        showListUser()
    }

    fun showListUser() {
        val layoutManager = LinearLayoutManager(this)

        viewModel.searchUser("arif")

        viewModel.searchList.observe(this) { listUser ->
            val adapter = UsersAdapter(ArrayList(listUser))
            binding.rvUser.layoutManager = layoutManager
            binding.rvUser.adapter = adapter
        }
    }
}