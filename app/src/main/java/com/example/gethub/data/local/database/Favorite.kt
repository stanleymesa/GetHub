package com.example.gethub.data.local.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "id_user")
    var idUser: Int = 0,

    @ColumnInfo(name = "login")
    var login: String = "",

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String = ""
) : Parcelable