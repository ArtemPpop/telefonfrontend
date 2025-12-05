package com.example.telefonfrontend.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoteModel(
    val id: Int,
    val poll: Int,
    val option: Int,
    val user: Int,

    @SerializedName("voted_at") val votedAt: String
) : Parcelable