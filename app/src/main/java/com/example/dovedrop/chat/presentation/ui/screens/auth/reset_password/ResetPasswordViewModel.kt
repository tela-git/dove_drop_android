package com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ResetPasswordViewModel: ViewModel() {
    private val _rpUIData = MutableStateFlow(RPUIData())
    val rpUiData = _rpUIData.asStateFlow()

    //Functions for updating UI
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

data class RPUIData(
    val otp: String = "",
    val passwordOne: String = "",
    val passwordTwo: String = "",
    val passwordOneVisibility: Boolean = false,
    val passwordTwoVisibility: Boolean = false,
    val buttonEnabled: Boolean = false
)