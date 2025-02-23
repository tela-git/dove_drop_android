package com.example.dovedrop.chat.data.di

import com.example.dovedrop.chat.data.repository.AuthRepositoryImpl
import com.example.dovedrop.chat.data.repository.ChatRepositoryImpl
import com.example.dovedrop.chat.data.repository.ContactRepositoryImpl
import com.example.dovedrop.chat.data.repository.EncryptedPrefs
import com.example.dovedrop.chat.domain.network.AuthRepository
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.network.ContactRepository
import com.example.dovedrop.chat.presentation.ui.components.otp.OtpViewModel
import com.example.dovedrop.chat.presentation.ui.screens.AppViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password.ResetPasswordViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.t_and_c.TAndCViewModel
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.ChatViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel
import com.example.dovedrop.chat.presentation.ui.screens.main.contacts.ContactsViewModel
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_detail.ChatRoomViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private const val baseUrl = "https://dovedrop-app-6-d3690849cac2.herokuapp.com/"

val appModule = module {

    single {
        HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
                headers {

                }
            }
            defaultRequest {
                url(baseUrl)
            }
        }
    }
    single<EncryptedPrefs> { EncryptedPrefs(androidContext()) }
    single<AuthRepository> {AuthRepositoryImpl(get())}
    single<ChatRepository> {ChatRepositoryImpl(get(), get())}
    single<ContactRepository> { ContactRepositoryImpl(get(), get()) }

    viewModelOf(::AuthViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::OtpViewModel)
    viewModelOf(::ChatViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::TAndCViewModel)
    viewModelOf(::ContactsViewModel)
    viewModelOf(::ChatRoomViewModel)

}