package com.example.telefonfrontend.data.repository

import com.example.telefonfrontend.data.model.PollModel
import com.example.telefonfrontend.data.network.getRetrofitClient
import retrofit2.Response

class PollRepository {
    private val api = getRetrofitClient()

    suspend fun getPolls(): Response<List<PollModel>> {
        return api.getPolls()
    }
}