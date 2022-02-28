package com.example.gethub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gethub.databinding.ItemRowUserBinding
import com.example.gethub.models.User

class UserAdapter(
    private val dataUser: ArrayList<User>,
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
            .load(context.resources.getIdentifier(user.avatar, "drawable", context.packageName))
            .circleCrop()
            .into(holder.binding.ivAvatar)

        holder.binding.tvUsername.text = user.username
        holder.binding.tvName.text = user.name

        holder.binding.root.setOnClickListener {
            onItemClickCallback.onItemClicked(user)
        }

        if (position == itemCount - 1) {
            holder.binding.rowSpace.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return dataUser.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(dataUser: User)
    }
}