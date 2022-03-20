package com.example.gethub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.data.remote.responses.FollowersFollowingResponseItem
import com.example.gethub.databinding.ItemRowFollowersBinding

class FollowersAdapter(
    private val listFollowersFollowing: List<FollowersFollowingResponseItem>,
    private val onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    inner class FollowersViewHolder(var binding: ItemRowFollowersBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding =
            ItemRowFollowersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        val dataUser = listFollowersFollowing[position]
        val context = holder.binding.root
        Glide.with(context)
            .load(dataUser.avatarUrl)
            .placeholder(R.drawable.ic_baseline_person_pin_24)
            .circleCrop()
            .into(holder.binding.ivAvatar)
        holder.binding.tvUsername.text = dataUser.login

        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(dataUser.login)
        }
    }

    override fun getItemCount(): Int {
        return listFollowersFollowing.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}