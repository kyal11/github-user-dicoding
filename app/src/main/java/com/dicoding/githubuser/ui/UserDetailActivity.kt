package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.githubuser.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPagerFollow
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabsFollow
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
    companion object {
        const val DETAIL_USER = "detail_user"
        var username = String()

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
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
            binding.tvTotalRepository.text = detailUser?.publicRepos.toString()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}