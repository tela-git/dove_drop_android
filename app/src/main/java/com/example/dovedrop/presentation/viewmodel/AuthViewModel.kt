package com.example.dovedrop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(): ViewModel() {
    private val _authState = MutableStateFlow(AuthState(AppAuthState.UnAuthenticated))
    val authState = _authState.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun loginUser() {
        _authState.update {
            it.copy(
                authState = AppAuthState.Authenticated
            )
        }
    }
    fun logoutUser() {
        _authState.update {
            it.copy(
                authState = AppAuthState.UnAuthenticated
            )
        }
    }

    fun changeEnterEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email
            )
        }
    }
    fun changeEnterPassword(password: String) {
        _uiState.update {
            it.copy(
                password = password
            )
        }
    }
    fun changePasswordVisibility(isVisible: Boolean) {
        _uiState.update {
            it.copy(
                isPasswordVisible = isVisible
            )
        }
    }
}

data class UiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)
data class AuthState(
    val authState: AppAuthState,
    val userName: String = ""
)

sealed class AppAuthState {
    object Authenticated: AppAuthState()
    object UnAuthenticated: AppAuthState()
    object Loading: AppAuthState()
}