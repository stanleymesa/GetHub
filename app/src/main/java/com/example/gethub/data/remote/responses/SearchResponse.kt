package com.example.gethub.data.remote.responses

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @field:SerializedName("items")
    val items: List<ItemsItem>
)

data class ItemsItem(

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("login")
    val login: String
)
