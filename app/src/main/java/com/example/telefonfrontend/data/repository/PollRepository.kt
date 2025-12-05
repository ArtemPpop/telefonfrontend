package com.example.telefonfrontend.data.repository

import com.example.telefonfrontend.data.model.PollModel
import com.example.telefonfrontend.data.network.getRetrofitClient
import retrofit2.Response

class PollRepository {
    suspend fun getPolls(): Response<List<PollModel>> {
        return getRetrofitClient().getPolls()
    }

    suspend fun getPollById(id: Int): Response<PollModel> {
        return getRetrofitClient().getPollById(id)
    }
}