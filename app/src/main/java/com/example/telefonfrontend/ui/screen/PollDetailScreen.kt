package com.example.telefonfrontend.ui.screen

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.telefonfrontend.viewmodel.PollViewModel
import com.example.telefonfrontend.viewmodel.VoteViewModel

@Composable
fun PollDetailScreen(
    navController: NavController,
    pollId: Int,
    pollViewModel: PollViewModel = viewModel(),
    voteViewModel: VoteViewModel = viewModel()
) {
    val poll = pollViewModel.currentPoll.collectAsState()
    val isLoading = pollViewModel.isLoading.collectAsState()
    val errorMessage = pollViewModel.errorMessage.collectAsState()
    val isVoting = voteViewModel.isLoading.collectAsState()

    LaunchedEffect(pollId) {
        pollViewModel.loadPollById(pollId)
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

            Text(
                text = poll.value?.title ?: "Опрос #$pollId",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (!poll.value?.description.isNullOrEmpty()) {
                Text(
                    text = poll.value?.description ?: "",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Выберите вариант:",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            when {
                errorMessage.value != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ошибка: ${errorMessage.value}",
                            color = Color.Red,
                            fontSize = 16.sp
                        )
                    }
                }

                isLoading.value -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                poll.value?.options?.isEmpty() == true -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(" вариантов нет")
                    }
                }

                else -> {
                    OptionSelectionList(
                        options = poll.value?.options ?: emptyList(),
                        onOptionSelected = { optionId ->
                            val voteModel = com.example.telefonfrontend.data.model.VoteModel(
                                id = 0,
                                poll = pollId,
                                option = optionId,
                                user = 0
                            )


                            voteViewModel.sendVote(voteModel) { success: Boolean ->
                                if (success) {

                                    navController.navigate("vote_results/$pollId")
                                }
                            }
                        },
                        isVoting = isVoting.value
                    )
                }
            }
        }
    }
}

@Composable
fun OptionSelectionList(
    options: List<com.example.telefonfrontend.data.model.OptionModel>,
    onOptionSelected: (Int) -> Unit,
    isVoting: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(options) { option ->
            OptionSelectionItem(
                option = option,
                onClick = { onOptionSelected(option.id) },
                isVoting = isVoting
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun OptionSelectionItem(
    option: com.example.telefonfrontend.data.model.OptionModel,
    onClick: () -> Unit,
    isVoting: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = !isVoting,
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = option.text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            if (option.votesCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Уже проголосовало: ${option.votesCount}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            if (isVoting) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}