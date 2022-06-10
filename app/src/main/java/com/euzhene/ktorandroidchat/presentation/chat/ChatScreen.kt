package com.euzhene.ktorandroidchat.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.euzhene.ktorandroidchat.domain.model.Message
import com.euzhene.ktorandroidchat.utils.toAMPMFieldDate
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    username: String
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    val state = viewModel.state.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            reverseLayout = true
        ) {
            item {
            }
            items(state.messages) { message ->
                Spacer(modifier = Modifier.height(32.dp))
                ChatMessage(message = message, username = username)
            }
        }

        ChatInput(
            viewModel.messageText.value,
            onValueChanged = viewModel::onMessageChange,
            onSendMessage = viewModel::sendMessage
        )
    }
}


@Composable
fun ChatMessage(message: Message, username: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier
            .width(200.dp)
            .drawBehind {
                val cornerRadius = 10.dp.toPx()
                val triangleHeight = 20.dp.toPx()
                val triangleWidth = 25.dp.toPx()
                val trianglePath = Path().apply {
                    if (username == message.username) {
                        moveTo(size.width, size.height - cornerRadius)
                        lineTo(size.width, size.height + triangleHeight)
                        lineTo(
                            size.width - triangleWidth,
                            size.height - cornerRadius
                        )
                    } else {
                        moveTo(0f, size.height - cornerRadius)
                        lineTo(0f, size.height + triangleHeight)
                        lineTo(triangleWidth, size.height - cornerRadius)
                    }
                    close()
                }
                drawPath(
                    trianglePath,
                    if (username == message.username) Color.Green else Color.DarkGray
                )
            }
            .background(
                if (username == message.username) Color.Green else Color.DarkGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(8.dp)
            .align(if (username == message.username) Alignment.CenterEnd else Alignment.CenterStart)
        ) {
            Text(
                text = message.username,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = message.text,
                color = Color.White
            )
            Text(
                text = toAMPMFieldDate(message.timestamp),
                color = Color.White,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun ChatInput(
    value: String,
    onValueChanged: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Row {
        TextField(
            value = value,
            onValueChange = onValueChanged,
            placeholder = { Text("Enter a message") },
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onSendMessage) {
            Icon(imageVector = Icons.Default.Send, contentDescription = "Send")
        }
    }
}