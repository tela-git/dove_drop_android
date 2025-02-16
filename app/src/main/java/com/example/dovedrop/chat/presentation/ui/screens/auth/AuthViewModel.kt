package com.example.dovedrop.chat.presentation.ui.screens.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.chat.data.network.dto.auth.LoginError
import com.example.dovedrop.chat.data.network.dto.auth.LoginRequestData
import com.example.dovedrop.chat.data.network.dto.auth.SignUpError
import com.example.dovedrop.chat.data.network.dto.auth.SignUpRequestData
import com.example.dovedrop.chat.data.repository.EncryptedPrefs
import com.example.dovedrop.chat.domain.network.AuthRepository
import com.example.dovedrop.chat.domain.util.Result
import com.example.dovedrop.chat.utils.emailRegex
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val AUTH_TAG_VIEWMODEL = "AuthTag"

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val prefs: EncryptedPrefs
): ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthorized)
    val authState = _authState.asStateFlow()

    private val _toastChannel = Channel<String>(Channel.BUFFERED)
    val toastFlow = _toastChannel.receiveAsFlow()

    private val _loginUIData = MutableStateFlow(LoginUIData())
    val loginUIData = _loginUIData.asStateFlow()

    private val _signUpUIData = MutableStateFlow(SignUpUIData())
    val signUpUIData = _signUpUIData.asStateFlow()


    fun loginUser() {
        val loginRequestData = LoginRequestData(
            email = loginUIData.value.email,
            password = loginUIData.value.password
        )
        _loginUIData.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = authRepository.login(
                loginRequest = loginRequestData
            )
            when(response) {
                is Result.Success -> {
                    prefs.saveToken(response.data) //saves the token to the EncryptedPrefs I created.
                    prefs.updateLoginStatus(isLoggedIn = true)
                    _toastChannel.send("Login Successful")
                    _authState.value = AuthState.Authorized
                    _loginUIData.update { it.copy(isLoading = false) }
                    Log.d(AUTH_TAG_VIEWMODEL, "Login success : ${response.data}")
                }
                is Result.Error -> {
                    _loginUIData.update { it.copy(isLoading = false) }
                    _authState.value = AuthState.UnAuthorized
                    prefs.updateLoginStatus(isLoggedIn = false)
                    when(response.error) {
                        LoginError.UNKNOWN_ERROR -> {
                            _toastChannel.send("Unknown Error")
                        }
                        LoginError.EMAIL_VERIFY_ERROR -> {
                            _toastChannel.send("Please verify your email!")
                        }
                        LoginError.INVALID_CRED_ERROR -> {
                            _toastChannel.send("Invalid credentials")
                        }
                        LoginError.TOKEN_ERROR -> {
                            _toastChannel.send("Something went wrong, try after sometime.")
                        }
                    }
                }
            }
        }
    }
    fun isUserLoggedIn(): Boolean {
        return prefs.getLoginStatus()
    }
    fun logOutUser() {
        prefs.updateLoginStatus(isLoggedIn = false)
        prefs.clearToken()
    }
    fun signUpUser() {
        val signUpRequestData = SignUpRequestData(
            name = signUpUIData.value.name,
            email = signUpUIData.value.email,
            password = signUpUIData.value.password
        )

        _signUpUIData.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val response = authRepository.signup(
                signUpRequest = signUpRequestData
            )
            when(response) {
                is Result.Success -> {
                    _toastChannel.send("OTP sent Successfully")
                    _signUpUIData.update { it.copy(isLoading = false) }
                    Log.d(AUTH_TAG_VIEWMODEL, "OTP SENT SUCCESS : ${response.data}")
                }
                is Result.Error -> {
                    _signUpUIData.update { it.copy(isLoading = false) }
                    _authState.value = AuthState.UnAuthorized
                    when(response.error) {
                        is SignUpError.InvalidCredFormat -> {
                            _toastChannel.send("Please enter data in a valid format!")
                        }
                        is SignUpError.UserAlreadyExists -> {
                            _toastChannel.send("User already exists with the same email!")
                        }
                        is SignUpError.ErrorSendingOTP -> {
                            _toastChannel.send("Unable to send OTP. Please try after some time.")
                        }
                        is SignUpError.UnknownError -> {
                            _toastChannel.send("Something went wrong, try after sometime.")
                        }
                    }
                }
            }
        }
    }

    //Methods for updating login details entered
    fun changeEnteredEmailLogin(email: String) {
        _loginUIData.update { it.copy(email = email) }
        changeLoginButtonEnabling()
    }
    fun updateEnteredPasswordLogin(password: String) {
        _loginUIData.update { it.copy(password = password) }
        changeLoginButtonEnabling()
    }

    //Methods for updating signup details entered
    fun updateEnteredNameSignUp(name: String) {
        _signUpUIData.update { it.copy(name = name) }
        changeSignUpButtonEnabling()
    }
    fun updateEnteredEmailSignUp(email: String) {
        _signUpUIData.update { it.copy(email = email) }
        changeSignUpButtonEnabling()
    }
    fun updateEnteredPasswordSignUp(password: String) {
        _signUpUIData.update { it.copy(password = password) }
        changeSignUpButtonEnabling()
    }
    fun changePasswordVisibility(bool: Boolean) {
        _loginUIData.update { state->
            state.copy(passwordVisibility = bool)
        }
        _signUpUIData.update { state->
            state.copy(passwordVisibility = bool)
        }
    }
    private fun changeSignUpButtonEnabling() {
        _signUpUIData.update { state->
            state.copy(
                buttonEnabled =
                signUpUIData.value.name.length >=3 &&
                signUpUIData.value.email.matches(emailRegex) &&
                signUpUIData.value.password.length >=6
            )
        }
    }
    private fun changeLoginButtonEnabling() {
        _loginUIData.update { state->
            state.copy(
                buttonEnabled =
                loginUIData.value.email.matches(emailRegex) &&
                loginUIData.value.password.isNotEmpty()
            )
        }
    }

}

sealed class AuthState {
    data object Authorized: AuthState()
    data object UnAuthorized: AuthState()
}

data class LoginUIData(
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false
)

data class SignUpUIData(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisibility: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)
