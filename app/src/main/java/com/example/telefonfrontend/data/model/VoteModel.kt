package com.example.telefonfrontend.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoteModel(
    val id: Int = 0,
    val poll: Int,
    val option: Int,
    val user: Int = 0,

    @SerializedName("voted_at")
    val votedAt: String = "",

    @SerializedName("option_text")
    val optionText: String? = null,

    @SerializedName("user_name")
    val userName: String? = null
) : Parcelable