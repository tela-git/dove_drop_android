package com.example.dovedrop.chat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val supabase: SupabaseClient
): ViewModel() {

    private val _authState = MutableStateFlow(AuthState(AppAuthState.UnAuthenticated))
    val authState = _authState.asStateFlow()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    private val _signUpUiState = MutableStateFlow(SignUpUiState())
    val signUpUiState = _signUpUiState.asStateFlow()

    fun loginUser() {
        if(supabase != null) {
            try {
                viewModelScope.launch {
                    supabase!!.auth.signInWith(Email) {
                        email = loginUiState.value.email
                        password = loginUiState.value.password
                    }
                }
                Log.d("LETTER","Logout function called")
            } catch (e: Exception) {
                Log.d("LETTER", "Error Message: ${e.localizedMessage}")
            }
        }
    }
    fun logoutUser() {
        if(supabase != null){
            try {
                viewModelScope.launch {
                    supabase!!.auth.signOut()
                }
            } catch (e: Exception) {
                Log.d("LETTER", "Error Message: ${e.localizedMessage}")
            }
        }
    }

    fun signUpUser() {
        if(supabase != null) {
            try {
                viewModelScope.launch {
                    supabase!!.auth.signUpWith(Email) {
                        email = signUpUiState.value.email
                        password = signUpUiState.value.password
                        data = buildJsonObject {
                            put("name", signUpUiState.value.name)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("LETTER", "Error Message: ${e.localizedMessage}")
            }
        }
    }

    fun changeEnteredLoginEmail(email: String) {
        _loginUiState.update { it.copy(email = email) }
    }
    fun changeEnteredLoginPassword(password: String) {
        _loginUiState.update { it.copy(password = password) }
    }
    fun changeEnteredSignUpEmail(email: String) {
        _signUpUiState.update { it.copy(email = email) }
    }
    fun changeEnteredSignUpPassword(password: String) {
        _signUpUiState.update { it.copy(password = password) }
    }
    fun changeEnteredSignUpName(name: String) {
        _signUpUiState.update { it.copy(name = name) }
    }
    fun changePasswordVisibility(isVisible: Boolean) {
        _loginUiState.update { it.copy(isPasswordVisible = isVisible) }
        _signUpUiState.update { it.copy(isPasswordVisible = isVisible) }
    }
    fun clearLoginCred() {
        _loginUiState.update { LoginUiState() }
    }
    fun clearSignUpCred() {
        _signUpUiState.update { SignUpUiState() }
    }
    init {
        viewModelScope.launch {
            if(supabase != null) {
                try {
                    supabase!!.auth.sessionStatus.collect { status ->
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
                } catch (e: Exception) {
                    Log.d("LETTER", "Error Message: ${e.localizedMessage}")
                }
            }
        }
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)
data class AuthState(
    val authState: AppAuthState,
    val userName: String = ""
)

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val name: String = ""
)

sealed class AppAuthState {
    object Authenticated: AppAuthState()
    object UnAuthenticated: AppAuthState()
    object Loading: AppAuthState()
}
