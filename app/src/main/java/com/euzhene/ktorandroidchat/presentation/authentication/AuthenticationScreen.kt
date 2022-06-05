package com.euzhene.ktorandroidchat.presentation.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.onJoinChat.collectLatest {
            onNavigate("chat_screen/${it.login}/${it.password}")
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.loginText.value,
                onValueChange = viewModel::onLoginChange,
                placeholder = {
                    Text("Enter a login")
                })
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.passwordText.value,
                onValueChange = viewModel::onPasswordChange,
                placeholder = {
                    Text("Enter a password")
                })
            Spacer(Modifier.height(8.dp))
            Button(onClick = viewModel::onJoinClick) {
                Text("Join")
            }

        }
    }
}