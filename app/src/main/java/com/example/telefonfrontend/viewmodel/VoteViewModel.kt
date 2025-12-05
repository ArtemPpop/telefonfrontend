// viewmodel/VoteViewModel.kt
package com.example.telefonfrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telefonfrontend.data.model.VoteModel
import com.example.telefonfrontend.data.repository.VoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VoteViewModel(
    private val repository: VoteRepository = VoteRepository()
) : ViewModel() {

    // Nullable как в примере
    private val _votes = MutableStateFlow<List<VoteModel>?>(null)
    val votes: StateFlow<List<VoteModel>?> get() = _votes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun loadVotes(pollId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val response = repository.getVotesByPoll(pollId)

            if (response.isSuccessful) {
                _votes.value = response.body() ?: emptyList()
            } else {
                _errorMessage.value = "Ошибка загрузки голосов: ${response.code()}"
            }

            _isLoading.value = false
        }
    }

    fun clearVotes() {
        _votes.value = null
        _errorMessage.value = null
    }
}