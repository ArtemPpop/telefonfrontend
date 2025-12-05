package com.example.telefonfrontend.data.network

import com.example.telefonfrontend.data.model.OptionModel
import com.example.telefonfrontend.data.model.PollModel
import com.example.telefonfrontend.data.model.VoteModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    // GET /api/polls/
    @GET("polls/")
    suspend fun getPolls(): Response<List<PollModel>>

    // GET /api/polls/{id}/
    @GET("polls/{id}/")
    suspend fun getPollById(
        @Path("id") pollId: Int
    ): Response<PollModel>

    // POST /api/votes/
    @POST("votes/")
    suspend fun sendVote(
        @Body vote: VoteModel
    ): Response<VoteModel>
}

fun getRetrofitClient(): ApiService {
    val client = OkHttpClient.Builder()
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    return retrofit.create(ApiService::class.java)
}


