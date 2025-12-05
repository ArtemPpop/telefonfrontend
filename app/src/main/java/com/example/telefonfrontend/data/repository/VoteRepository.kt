// data/repository/VoteRepository.kt
package com.example.telefonfrontend.data.repository

import com.example.telefonfrontend.data.model.VoteModel
import com.example.telefonfrontend.data.network.getRetrofitClient
import retrofit2.Response

class VoteRepository {
    suspend fun getVotesByPoll(pollId: Int): Response<List<VoteModel>> {
        return getRetrofitClient().getVotesByPoll(pollId)
    }

    suspend fun sendVote(vote: VoteModel): Response<VoteModel> {
        return getRetrofitClient().sendVote(vote)
    }
}