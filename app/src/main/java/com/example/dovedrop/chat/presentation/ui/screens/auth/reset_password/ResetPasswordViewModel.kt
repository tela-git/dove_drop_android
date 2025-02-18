package com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.chat.data.network.dto.auth.FPResponseError
import com.example.dovedrop.chat.domain.network.AuthRepository
import com.example.dovedrop.chat.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _fpUIData = MutableStateFlow(FPUIData())
    val fpUIData = _fpUIData.asStateFlow()

    private val _fpToastChannel = Channel<String>(Channel.BUFFERED)
    val fpToastFlow = _fpToastChannel.receiveAsFlow()

    private val _rpUIData = MutableStateFlow(RPUIData())
    val rpUiData = _rpUIData.asStateFlow()


    fun sendOTPToEmail() {
        viewModelScope.launch {
            _fpUIData.update { state-> state.copy(isLoading = true) }
            val response = authRepository.sendOTPToUserEmail(fpUIData.value.email)
            when(response) {
                is Result.Success -> {
                    _fpToastChannel.send("OTP sent")
                    _fpUIData.update { state->
                        state.copy(
                            otpSent = true,
                            isLoading = false
                        )
                    }
                }
                is Result.Error -> {
                    _fpUIData.update { it.copy(isLoading = false, otpSent = false, email = "") }
                    when(response.error) {
                        is FPResponseError.InvalidRequestFormat -> {
                            _fpToastChannel.send("Please enter valid data!")
                        }
                        FPResponseError.InvalidRequest -> {
                            _fpToastChannel.send("Invalid request!")
                        }
                        FPResponseError.ServerError -> {
                            _fpToastChannel.send("OOPS!, Please try after sometime")
                        }
                        FPResponseError.UnknownError -> {
                            _fpToastChannel.send("OOPS!, Try after sometime")
                        }
                    }
                }
            }
        }
    }

    //Functions for updating FP UI
    fun updateEnteredEmailFP(email: String) {
        _fpUIData.update { state->
            state.copy(
                email = email
            )
        }
    }
    fun resetFPState() {
        _fpUIData.update {
            it.copy(
                email = "",
                otpSent = false,
                isLoading = false
            )
        }
    }

    //Functions for updating RP UI
    fun updateEnteredOTP(otp: String) {
        _rpUIData.update { it.copy(otp = otp) }
        changeButtonEnabling()
    }
    fun updateEnteredPasswordOne(password: String) {
        _rpUIData.update { it.copy(passwordOne = password) }
        changeButtonEnabling()
    }
    fun updateEnteredPasswordTwo(password: String) {
            _rpUIData.update { it.copy(passwordTwo = password) }
            changeButtonEnabling()
    }
    fun changePasswordOneVisibility(bool: Boolean) {
        _rpUIData.update { it.copy(passwordOneVisibility = bool) }
    }
    fun changePasswordTwoVisibility(bool: Boolean) {
        _rpUIData.update { it.copy(passwordTwoVisibility = bool) }
    }
    private fun changeButtonEnabling() {
        _rpUIData.update { state->
            state.copy(
                buttonEnabled =
                rpUiData.value.otp.length == 6 &&
                rpUiData.value.passwordOne == rpUiData.value.passwordTwo
                        && rpUiData.value.passwordOne.length >= 6
            )
        }
    }
}

data class FPUIData(
    val email: String = "",
    val otpSent: Boolean = false,
    val isLoading: Boolean = false,
)

data class RPUIData(
    val otp: String = "",
    val passwordOne: String = "",
    val passwordTwo: String = "",
    val passwordOneVisibility: Boolean = false,
    val passwordTwoVisibility: Boolean = false,
    val buttonEnabled: Boolean = false
)