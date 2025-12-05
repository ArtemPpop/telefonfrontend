// ui/screen/PollListScreen.kt
package com.example.telefonfrontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.telefonfrontend.data.model.PollModel
import com.example.telefonfrontend.viewmodel.PollViewModel

@Composable
fun PollListScreen(
    navController: NavController,
    viewModel: PollViewModel = viewModel()
) {
    val polls = viewModel.polls.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // Автоматическая загрузка при открытии экрана
    LaunchedEffect(Unit) {
        viewModel.loadPolls()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Кнопка обновить
            Button(
                onClick = { viewModel.loadPolls() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Обновить опросы")
            }

            // Обработка состояний
            when {
                errorMessage.value != null -> {
                    ErrorState(errorMessage = errorMessage.value!!)
                }

                isLoading.value -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(100.dp),
                            color = Color.Blue,
                            trackColor = Color.Red
                        )
                    }
                }

                else -> {
                    PollListContent(
                        navController = navController,
                        pollList = polls.value
                    )
                }
            }
        }
    }
}

@Composable
fun PollListContent(
    navController: NavController,
    pollList: List<PollModel>?
) {
    LazyColumn {
        pollList?.let { polls ->
            items(polls.size) { index ->
                val poll = polls[index]
                PollCell(
                    modifier = Modifier.fillMaxWidth(),
                    index = index + 1,
                    poll = poll,
                    onClick = {
                        // ТОЧНО как в примере с покемонами!
                        navController.navigate("vote_list/${poll.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun PollCell(
    modifier: Modifier = Modifier,
    index: Int,
    poll: com.example.telefonfrontend.data.model.PollModel,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "$index. ${poll.title}",
                fontSize = 18.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!poll.description.isNullOrEmpty()) {
                Text(
                    text = poll.description,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "ID: ${poll.id} | Автор: ${poll.authorName}",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    errorMessage: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ошибка!",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = errorMessage,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}