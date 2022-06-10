package com.euzhene.ktorandroidchat.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.euzhene.ktorandroidchat.presentation.authentication.AuthenticationScreen
import com.euzhene.ktorandroidchat.presentation.chat.ChatScreen
import com.euzhene.ktorandroidchat.ui.theme.KtorAndroidChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KtorAndroidChatTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "authentication_screen") {
                    composable("authentication_screen") {
                        AuthenticationScreen(onNavigate = navController::navigate)
                    }
                    composable(
                        "chat_screen/{login}/{password}/{username}",
                        arguments = listOf(
                            navArgument("login") {
                                type = NavType.StringType
                                nullable = true
                            },
                            navArgument("password") {
                                type = NavType.StringType
                                nullable = true
                            },
                            navArgument("username") {
                                type = NavType.StringType
                                nullable = true
                            }
                        )) {
                        ChatScreen(username = it.arguments?.getString("username") ?: "Guest")
                    }
                }
            }
        }
    }
}

