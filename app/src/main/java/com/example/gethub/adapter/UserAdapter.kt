package com.example.gethub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.databinding.ItemRowUserBinding
import com.example.gethub.responses.ItemsItem

class UserAdapter(
    private val dataUser: List<ItemsItem>,
    private val onItemClickCallback: OnItemClickCallback
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = dataUser[position]
        val context = holder.binding.root.context
        Glide.with(context)
            .load(user.avatarUrl)
            .placeholder(R.drawable.ic_baseline_person_pin_24)
            .circleCrop()
            .into(holder.binding.ivAvatar)

        holder.binding.tvUsername.text = user.login

        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(user.login)
        }

    }

    override fun getItemCount(): Int {
        return dataUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(username: String)
    }
}