package com.euzhene.ktorandroidchat.presentation.authentication

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        //todo find out why it's impossible to use more than one flow in launchedEffect
        viewModel.toastEvent.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.onJoinChat.collectLatest {
            onNavigate("chat_screen/${it.login}/${it.password}/${it.username}")
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
            InputInfo(
                value = viewModel.loginText.value,
                onValueChange = viewModel::onLoginChange,
                placeholder = "Enter a login"
            )
            Spacer(Modifier.height(8.dp))
            InputInfo(
                value = viewModel.passwordText.value,
                onValueChange = viewModel::onPasswordChange,
                placeholder = "Enter a password"
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = viewModel::onJoinClick) {
                Text("Join")
            }
            Spacer(Modifier.height(20.dp))
            Text("For registration")
            InputInfo(
                value = viewModel.usernameText.value,
                onValueChange = viewModel::onUsernameChange,
                placeholder = "Enter a username"
            )
            Button(onClick=viewModel::onRegisterClick){
                Text("Register")
            }
        }
    }
}

@Composable
fun InputInfo(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) }
    )
}