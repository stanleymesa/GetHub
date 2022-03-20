package com.example.gethub.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.gethub.data.local.database.Favorite

object FavoriteDiffCallback: DiffUtil.ItemCallback<Favorite>() {

    override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem.idUser == newItem.idUser
    }

    override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
        return oldItem == newItem
    }
}