package com.euzhene.ktorandroidchat.presentation.authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.euzhene.ktorandroidchat.domain.model.UserInfo
import com.euzhene.ktorandroidchat.domain.usecase.ChatUserUseCases
import com.euzhene.ktorandroidchat.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val chatUserUseCases: ChatUserUseCases,
) : ViewModel() {

    private val _loginText = mutableStateOf("")
    val loginText: State<String> = _loginText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _onJoinChat = MutableSharedFlow<UserInfo>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    private val _toastEvent =
        MutableSharedFlow<String>()   //shared flow is used for single events because it doesn't store any data within, so if the screen rotates, nothing will change.

    // On the other hand, stateFlow does change its value when rotating because it stores the last value. So it's like live data
    val toastEvent = _toastEvent.asSharedFlow()

    fun onLoginChange(login: String) {
        _loginText.value = login
    }

    fun onPasswordChange(password: String) {
        _passwordText.value = password
    }

    fun onUsernameChange(username: String) {
        _usernameText.value = username
    }

    fun onRegisterClick() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_loginText.value.isBlank() || _passwordText.value.isBlank() || _usernameText.value.isBlank()) {
                _toastEvent.emit("Fill out all inputs!")
                return@launch
            }
            val userInfo = UserInfo(
                loginText.value,
                passwordText.value,
                usernameText.value
            )
            when (val result = chatUserUseCases.registerUserUseCase(userInfo)) {
                is Resource.Success -> _onJoinChat.emit(result.data!!)
                is Resource.Error -> _toastEvent.emit(result.message ?: "Unknown error")
            }
        }
    }

    fun onJoinClick() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_loginText.value.isNotBlank() && _passwordText.value.isNotBlank()) {
                val userInfo =
                    UserInfo(_loginText.value, _passwordText.value, _usernameText.value)

                when (val userInfoRespond = chatUserUseCases.getUserInfoUseCase(userInfo)) {
                    is Resource.Success -> {
                        if (userInfoRespond.data == null) {
                            _toastEvent.emit("Incorrect login or password")
                        } else {
                            _onJoinChat.emit(userInfoRespond.data)
                        }
                    }
                    is Resource.Error -> _toastEvent.emit(
                        userInfoRespond.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}