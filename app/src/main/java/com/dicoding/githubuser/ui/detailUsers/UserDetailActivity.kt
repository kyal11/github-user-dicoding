package com.dicoding.githubuser.ui.detailUsers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.adapter.SectionsPagerAdapter
import com.dicoding.githubuser.data.local.entity.FavoriteUsers
import com.dicoding.githubuser.databinding.ActivityUserDetailBinding
import com.dicoding.githubuser.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityUserDetailBinding
    private var favoriteUsers: FavoriteUsers? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (viewModel.detailUsername.isEmpty()) {
            username = intent.getStringExtra(DETAIL_USER).toString()
            viewModel.detailUsername = username
        }
        showDetail()
        viewModel.isLoading.observe(this, this::showLoading)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPagerFollow
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabsFollow
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        viewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnFavorite.setOnClickListener(this)
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
        viewModel.userDetail.observe(this) { detailUser ->
            favoriteUsers = detailUser?.login?.let { FavoriteUsers(it, detailUser?.avatarUrl) }
            Glide.with(this)
                .load(detailUser?.avatarUrl)
                .skipMemoryCache(true)
                .into(binding.ivProfil)
            binding.tvUsername.text = detailUser?.login
            binding.tvName?.text = detailUser?.name
            binding.tvTotalFollower.text = detailUser?.followers.toString()
            binding.tvTotalFollowing.text = detailUser?.following.toString()
            binding.tvTotalRepository.text = detailUser?.publicRepos.toString()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_favorite -> {
                favoriteUsers?.let {
                    viewModel.insertFavoriteUsers(it)
                }
                Log.e("Favorite", "DATA: ${favoriteUsers}")
            }
        }
    }

    override fun onDestroy() {
        favoriteUsers = null
        super.onDestroy()
    }
}