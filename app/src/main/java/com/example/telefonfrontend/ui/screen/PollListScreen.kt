package com.example.telefonfrontend.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.telefonfrontend.viewmodel.PollViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollListScreen(viewModel: PollViewModel = viewModel ()) {

    val polls by viewModel.polls.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    LaunchedEffect (Unit) {
        viewModel.loadPolls()
    }

    Scaffold (
        topBar = {
            TopAppBar(title = { Text("Опросы") })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error")
                else -> LazyColumn() {
                    items(polls) { poll ->
                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column (Modifier.padding(16.dp)) {
                                Text(poll.title, style = MaterialTheme.typography.titleLarge)
                                Spacer(Modifier.height(4.dp))
                                Text(poll.description ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollViewModel(viewModel: PollViewModel = viewModel()) {

    val polls by viewModel.polls.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPolls()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Опросы") })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error")
                else -> LazyColumn {
                    items(polls) { poll ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(Modifier.padding(16.dp)) {
                                Text(poll.title, style = MaterialTheme.typography.titleLarge)
                                Spacer(Modifier.height(4.dp))
                                Text(poll.description ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}