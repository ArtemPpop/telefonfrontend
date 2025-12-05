package com.example.telefonfrontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.telefonfrontend.data.model.PollModel
import com.example.telefonfrontend.data.repository.PollRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PollViewModel(
    private val repository: PollRepository = PollRepository()
) : ViewModel() {

    private val _polls = MutableStateFlow<List<PollModel>?>(null)
    val polls: StateFlow<List<PollModel>?> get() = _polls

    private val _currentPoll = MutableStateFlow<PollModel?>(null)
    val currentPoll: StateFlow<PollModel?> get() = _currentPoll

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun loadPolls() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val response = repository.getPolls()

            if (response.isSuccessful) {
                _polls.value = response.body() ?: emptyList()
            } else {
                _errorMessage.value = "Ошибка загрузки: ${response.code()}"
            }

            _isLoading.value = false
        }
    }

    fun loadPollById(pollId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val response = repository.getPollById(pollId)

            if (response.isSuccessful) {
                _currentPoll.value = response.body()
            } else {
                _errorMessage.value = "Ошибка загрузки опроса: ${response.code()}"
            }

            _isLoading.value = false
        }
    }

    fun clearCurrentPoll() {
        _currentPoll.value = null
    }
}