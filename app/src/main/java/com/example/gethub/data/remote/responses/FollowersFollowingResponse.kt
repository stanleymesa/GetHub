package com.example.gethub.data.remote.responses

import com.google.gson.annotations.SerializedName

data class FollowersFollowingResponseItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String
)
