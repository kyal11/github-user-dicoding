package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.githubuser.viewmodel.MainViewModel

class UserDetailActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        username = intent.getStringExtra(DETAIL_USER).toString()
        showDetail()
        viewModel.isLoading.observe(this, this::showLoading)

    }
    companion object {
        const val DETAIL_USER = "detail_user"
        var username = String()
    }

    fun showDetail() {
        viewModel.detailUser(username)
        viewModel.userDetail.observe(this) { detailUser ->
            Glide.with(this)
                .load(detailUser?.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.ivAvatar)
            binding.tvUsername.text = detailUser?.login
            binding.tvName.text = detailUser?.name
            binding.tvTotalFollower.text = detailUser?.followers.toString()
            binding.tvTotalFollowing.text = detailUser?.following.toString()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}