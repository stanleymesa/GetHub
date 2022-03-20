package com.example.gethub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gethub.R
import com.example.gethub.data.local.database.Favorite
import com.example.gethub.databinding.ItemRowFavoriteBinding
import com.example.gethub.ui.diffutil.FavoriteDiffCallback

class FavoriteAdapter(
    private val onItemClicked: OnItemClicked,
    private val isCheckboxAllSelected: Boolean
) : ListAdapter<Favorite, FavoriteAdapter.FavoriteViewHolder>(FavoriteDiffCallback) {

    private val listUserToBeDelete: ArrayList<Favorite> = ArrayList()

    inner class FavoriteViewHolder(var binding: ItemRowFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemRowFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        val context = holder.binding.root.context
        Glide.with(context)
            .load(favoriteUser.avatarUrl)
            .placeholder(R.drawable.ic_baseline_person_pin_24)
            .circleCrop()
            .into(holder.binding.ivAvatar)

        holder.binding.tvUsername.text = favoriteUser.login

        if (isCheckboxAllSelected) {
            holder.binding.checkbox.isChecked = true
            listUserToBeDelete.clear()
            for (i in 0 until itemCount) {
                listUserToBeDelete.add(getItem(i))
            }
            onItemClicked.onUsersToBeDelete(listUserToBeDelete)
        }

        holder.binding.checkbox.setOnClickListener {
            if (holder.binding.checkbox.isChecked) {
                listUserToBeDelete.add(favoriteUser)
            } else {
                listUserToBeDelete.removeAt(listUserToBeDelete.indexOf(favoriteUser))
            }
            onItemClicked.onUsersToBeDelete(listUserToBeDelete)
        }

        holder.binding.root.setOnClickListener {
            onItemClicked.onItemClicked(favoriteUser.login)
        }


    }

    interface OnItemClicked {
        fun onUsersToBeDelete(listUserToBeDelete: ArrayList<Favorite>)
        fun onItemClicked(username: String)
    }
}