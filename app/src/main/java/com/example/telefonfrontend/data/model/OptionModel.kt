package com.example.telefonfrontend.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class OptionModel(
    val id: Int,
    val text: String,
    @SerializedName("votes_count") val votesCount: Int
) : Parcelable