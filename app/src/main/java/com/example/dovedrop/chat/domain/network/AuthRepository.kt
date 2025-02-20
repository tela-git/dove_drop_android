package com.example.dovedrop.chat.domain.network

import com.example.dovedrop.chat.data.model.auth.VerifyEmailError
import com.example.dovedrop.chat.data.model.auth.FPResponseError
import com.example.dovedrop.chat.data.model.auth.ResetPasswordError
import com.example.dovedrop.chat.data.network.dto.auth.LoginError
import com.example.dovedrop.chat.data.network.dto.auth.LoginRequestData
import com.example.dovedrop.chat.data.model.auth.SignUpError
import com.example.dovedrop.chat.data.network.dto.auth.SignUpRequestData
import com.example.dovedrop.chat.domain.util.Result

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequestData): Result<String, LoginError>
    suspend fun signup(signUpRequest: SignUpRequestData): Result<String?, SignUpError>
    suspend fun emailVerify(email: String, otp: String): Result<String, VerifyEmailError>
    suspend fun sendOTPToUserEmail(email: String): Result<String, FPResponseError>
    suspend fun resetPassword(otp: String, email: String, newPassword: String): Result<String, ResetPasswordError>
}