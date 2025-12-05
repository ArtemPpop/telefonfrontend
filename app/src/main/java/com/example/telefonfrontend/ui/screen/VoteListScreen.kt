// ui/screen/VoteListScreen.kt
package com.example.telefonfrontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.telefonfrontend.viewmodel.VoteViewModel

@Composable
fun VoteListScreen(
    navController: NavController,
    pollId: Int,
    viewModel: VoteViewModel = viewModel()
) {
    val voteList = viewModel.votes.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    // ТОЧНО как в примере с покемонами!
    LaunchedEffect(pollId) {
        viewModel.loadVotes(pollId)
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
            // Кнопка назад
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = { navController.popBackStack() }
                ) {
                    Text("Назад к опросам")
                }
            }

            // Заголовок
            Text(
                text = "Варианты для голосования #$pollId",
                fontSize = 20.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

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

                voteList.value?.isEmpty() == true -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Нет голосов",
                            fontSize = 18.sp
                        )
                    }
                }

                else -> {
                    VoteListContent(voteList = voteList.value ?: emptyList())
                }
            }
        }
    }
}

@Composable
fun VoteListContent(voteList: List<com.example.telefonfrontend.data.model.VoteModel>) {
    LazyColumn {
        items(voteList.size) { index ->
            val vote = voteList[index]
            VoteCell(
                index = index + 1,
                vote = vote
            )
        }
    }
}

@Composable
fun VoteCell(
    index: Int,
    vote: com.example.telefonfrontend.data.model.VoteModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Голос #$index",
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Вариант: ${vote.option}",
                    fontSize = 14.sp
                )

                Text(
                    text = "Пользователь: ${vote.user}",
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Опрос: ${vote.poll}",
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = vote.votedAt,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}