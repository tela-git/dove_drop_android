package com.example.dovedrop.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val supabase: SupabaseClient
): ViewModel() {
    private val _authState = MutableStateFlow(AuthState(AppAuthState.UnAuthenticated))
    val authState = _authState.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()


    fun loginUser() {
        viewModelScope.launch {
            supabase.auth.signInWith(Email) {
                email = uiState.value.email
                password = uiState.value.password
            }
        }
    }
    fun logoutUser() {
        viewModelScope.launch {
            supabase.auth.signOut()
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

    init {
        viewModelScope.launch {
            supabase.auth.sessionStatus.collect { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        _authState.update { state ->
                            state.copy(
                                authState = AppAuthState.Authenticated
                            )
                        }
                    }
                    is SessionStatus.Initializing -> {
                        _authState.update { it.copy(authState = AppAuthState.Loading) }
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _authState.update { it.copy(authState = AppAuthState.UnAuthenticated) }
                    }
                    is SessionStatus.RefreshFailure -> {}
                }
            }
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