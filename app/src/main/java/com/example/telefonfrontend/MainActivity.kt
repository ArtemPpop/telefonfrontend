package com.example.telefonfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.telefonfrontend.ui.screen.*
import com.example.telefonfrontend.ui.theme.TelefonfrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelefonfrontendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TelefonApp()
                }
            }
        }
    }
}

@Composable
fun TelefonApp(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = "poll_list") {
        composable("poll_list") {
            PollListScreen(navController = navController)
        }

        composable(
            "poll_detail/{pollId}",
            arguments = listOf(navArgument("pollId") {
                type = NavType.IntType
            }),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("pollId")
                ?.let { pollId ->
                    PollDetailScreen(
                        navController = navController,
                        pollId = pollId
                    )
                }
        }

        composable(
            "vote_results/{pollId}",
            arguments = listOf(navArgument("pollId") {
                type = NavType.IntType
            }),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            }
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("pollId")
                ?.let { pollId ->
                    VoteListScreen(
                        navController = navController,
                        pollId = pollId
                    )
                }
        }
    }
}