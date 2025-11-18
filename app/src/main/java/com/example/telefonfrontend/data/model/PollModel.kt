package com.example.telefonfrontend.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
    data class PollModel(
    val id: Int,
    val title: String,
    val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("author_name") val authorName: String?,
    val options: List<OptionModel>
    ) : Parcelable