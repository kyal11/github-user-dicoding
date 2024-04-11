package com.dicoding.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.FollowResponseItem
import com.dicoding.githubuser.databinding.ItemUserBinding

class FollowAdapter : ListAdapter<FollowResponseItem, FollowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowResponseItem) {
            binding.apply {
                tvUsername.text = user.login
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .skipMemoryCache(true)
                    .into(ivAvatar)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}