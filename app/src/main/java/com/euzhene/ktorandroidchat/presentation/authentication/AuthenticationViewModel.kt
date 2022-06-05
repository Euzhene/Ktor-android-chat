package com.euzhene.ktorandroidchat.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor() : ViewModel() {
    private val _loginText = mutableStateOf("")
    val loginText: State<String> = _loginText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _onJoinChat = MutableSharedFlow<UserInfo>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun onLoginChange(login: String) {
        _loginText.value = login
    }

    fun onPasswordChange(password: String) {
        _passwordText.value = password
    }

    fun onJoinClick() {
        viewModelScope.launch {
            if (_loginText.value.isNotBlank() && _passwordText.value.isNotBlank()) {
                _onJoinChat.emit(
                    UserInfo(_loginText.value, _passwordText.value)
                )
            }
        }
    }
}