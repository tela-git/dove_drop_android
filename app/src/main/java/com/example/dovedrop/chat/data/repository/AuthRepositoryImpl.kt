package com.example.dovedrop.chat.data.repository

import android.util.Log
import com.example.dovedrop.chat.data.model.VerifyEmailError
import com.example.dovedrop.chat.data.network.dto.auth.AuthResponse
import com.example.dovedrop.chat.data.network.dto.auth.FPResponseError
import com.example.dovedrop.chat.data.network.dto.auth.LoginError
import com.example.dovedrop.chat.data.network.dto.auth.LoginRequestData
import com.example.dovedrop.chat.data.network.dto.auth.SignUpError
import com.example.dovedrop.chat.data.network.dto.auth.SignUpRequestData
import com.example.dovedrop.chat.data.network.dto.auth.SimpleAPIResponse
import com.example.dovedrop.chat.data.network.dto.auth.VerifyEmailResponse
import com.example.dovedrop.chat.domain.network.AuthRepository
import com.example.dovedrop.chat.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.append
import io.ktor.http.contentType

private const val AUTH_TAG = "AuthTag"

class AuthRepositoryImpl(
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun login(
        loginRequest: LoginRequestData
    ):  Result<String, LoginError> {
        try {
            val apiResponse = httpClient.post("/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(loginRequest)
            }
            val response = apiResponse
                .body<AuthResponse<String>>()
           return if(response.status == "Success") {
               if(!response.data.isNullOrEmpty()) {
                   Result.Success(data = response.data)
               } else {
                   Log.d(AUTH_TAG, "Token is empty")
                   Result.Error(
                       LoginError.TOKEN_ERROR
                   )
               }
           } else {
               when(
                   apiResponse.status.value
               ) {
                   400 -> {
                       Result.Error(LoginError.INVALID_CRED_ERROR)
                   }
                   403 -> {
                       Result.Error(LoginError.EMAIL_VERIFY_ERROR)
                   }
                   else -> {
                       Result.Error(LoginError.UNKNOWN_ERROR)
                   }
               }
           }
        } catch (e: Exception) {
            Log.e(AUTH_TAG, "Error: $e")
            return Result.Error(LoginError.UNKNOWN_ERROR)
        }
    }

    override suspend fun signup(
        signUpRequest: SignUpRequestData
    ):  Result<String?, SignUpError> {
        try {
            val response = httpClient.post("/auth/signup") {
                contentType(ContentType.Application.Json)
                setBody(signUpRequest)
            }
                .body<AuthResponse<String>>()

            return if(response.status == "Success") {
                 Result.Success(data = response.data)
            } else {
                when(response.message) {
                    SignUpError.InvalidCredFormat.value -> {
                        Result.Error(SignUpError.InvalidCredFormat)
                    }
                    SignUpError.UserAlreadyExists.value -> {
                        Result.Error(SignUpError.UserAlreadyExists)
                    }
                    SignUpError.ErrorSendingOTP.value -> {
                        Result.Error(SignUpError.ErrorSendingOTP)
                    }
                    SignUpError.UnknownError.value -> {
                        Result.Error(SignUpError.UnknownError)
                    }
                    else -> {
                        Result.Error(SignUpError.UnknownError)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(AUTH_TAG, "Error: $e")
            return Result.Error(SignUpError.UnknownError)
        }
    }

    override suspend fun emailVerify(email: String, otp: String): Result<String, VerifyEmailError> {
        try {
            val response = httpClient.post("/auth/verify-email")
                .body<VerifyEmailResponse>()

            return if (response.status == "Success") {
                Result.Success("Email verified successfully.")
            } else {
                when (response.status) {
                    VerifyEmailError.InvalidOTP.value -> {
                        Result.Error(VerifyEmailError.InvalidOTP)
                    }

                    VerifyEmailError.BadRequest.value -> {
                        Result.Error(VerifyEmailError.BadRequest)
                    }

                    VerifyEmailError.NoOTPToVerify.value -> {
                        Result.Error(VerifyEmailError.NoOTPToVerify)
                    }

                    VerifyEmailError.ServerError.value -> {
                        Result.Error(VerifyEmailError.ServerError)
                    }

                    else -> {
                        Result.Error(VerifyEmailError.UnknownError)
                    }
                }
            }
        } catch (e: Exception) {
            return Result.Error(VerifyEmailError.UnknownError)
        }
    }

    override suspend fun sendOTPToUserEmail(email: String): Result<String, FPResponseError> {
        return try {
            val response = httpClient
                .get(urlString = "/auth/forgot-password") {
                    url {
                        parameters.append("email", email)
                    }
                }
                .body<SimpleAPIResponse>()
            if (response.status == "Success") {
                Result.Success("OTP sent successfully")
            } else {
                when (response.message) {
                    FPResponseError.InvalidRequestFormat.value -> {
                        Result.Error(FPResponseError.InvalidRequestFormat)
                    }
                    FPResponseError.InvalidRequest.value -> {
                        Result.Error(FPResponseError.InvalidRequest)
                    }
                    FPResponseError.ServerError.value -> {
                        Result.Error(FPResponseError.ServerError)
                    }
                    else -> {
                        Log.e(AUTH_TAG, "Error: ${response.message}")
                        Result.Error(FPResponseError.UnknownError)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(AUTH_TAG, "Error: ${e.message}")
            Result.Error(FPResponseError.UnknownError)
        }
    }
}