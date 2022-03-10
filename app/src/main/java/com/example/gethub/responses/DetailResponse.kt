package com.example.gethub.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailResponse(

	@field:SerializedName("followers")
	val followers: Int? = 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = "",

	@field:SerializedName("following")
	val following: Int? = 0,

	@field:SerializedName("company")
	val company: String? = "",

	@field:SerializedName("location")
	val location: String? = "",

	@field:SerializedName("public_repos")
	val publicRepos: Int? = 0,

	@field:SerializedName("login")
	val login: String? = "",

	@field:SerializedName("name")
	val name: String? = ""
): Parcelable
