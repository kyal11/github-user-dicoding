package com.dicoding.githubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.adapter.UsersAdapter
import com.dicoding.githubuser.data.remote.response.ItemsItem
import com.dicoding.githubuser.databinding.ActivityFavoriteBinding
import com.dicoding.githubuser.ui.ViewModelFactory
import com.dicoding.githubuser.ui.detailUsers.UserDetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private val adapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.listFavorite.observe(this) {  users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it?.username, avatarUrl = it?.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                selectUser(data)
            }
        })
    }

    private fun setupRecyclerView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapter
    }

    private fun selectUser(user: ItemsItem) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.DETAIL_USER, user.login)
        startActivity(intent)
    }
}

