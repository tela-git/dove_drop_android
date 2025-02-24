package com.example.dovedrop.chat.data.repository

import android.util.Log
import com.example.dovedrop.chat.data.di.baseUrl
import com.example.dovedrop.chat.data.di.baseUrlWS
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.network.dto.auth.response.ApiResponse
import com.example.dovedrop.chat.domain.network.ChatRepository
import com.example.dovedrop.chat.domain.util.GetAllChatsError
import com.example.dovedrop.chat.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.invoke
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.http.parameters
import io.ktor.http.parametersOf
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

private const val AUTH_TAG = "AuthTag"

class ChatRepositoryImpl(
    private val httpClient: HttpClient,
    private val encryptedPrefs: EncryptedPrefs
): ChatRepository {
    override suspend fun getAllChatRooms() : Result<List<ChatRoom>?, GetAllChatsError> {
        return try {
            val response = httpClient
                .get("$baseUrl/user/getAllChatRooms") {
                url {
                    header(HttpHeaders.Authorization, "Bearer "+encryptedPrefs.getToken())
                }
            }.body<ApiResponse<List<ChatRoom>?>>()

            if(response.status == "Success") {
                Log.d("AuthTag", "status: ${response.status} and message: ${response.message} ")
                return Result.Success(response.data)
            } else {
                Log.d("AuthTag", "status: ${response.status} and message: ${response.message} ")
                return when(response.message) {
                    GetAllChatsError.UNAUTHORIZED.name -> {
                        Result.Error(GetAllChatsError.UNAUTHORIZED)
                    }
                    GetAllChatsError.SERVER_ERROR.name -> {
                        Result.Error(GetAllChatsError.SERVER_ERROR)
                    }
                    else -> {
                        Result.Error(GetAllChatsError.UNKNOWN_ERROR)
                    }
                }
            }
        } catch (e: Exception) {
            Result.Error(GetAllChatsError.UNKNOWN_ERROR)
        }
    }

    override suspend fun chat(chatRoomId: String, message: String): String {
        return try {
            val url = buildString {
                append(baseUrlWS)
                append("/chat/room")
                append("?chatRoomId=$chatRoomId")
                append("&timeStamp=${System.currentTimeMillis()}")
            }

            Log.d("AuthTag", "Connecting to WebSocket: $url")
            httpClient.webSocket(
                urlString = url,
                request = {
                    header(HttpHeaders.Authorization, "Bearer ${encryptedPrefs.getToken()}")
                }
            ) {
                Log.d("AuthTag", "WebSocket connection established")

                Log.d("AuthTag", "Sending message: $message")

                send(Frame.Text(message))
                val frame = incoming.receive()
                if (frame is Frame.Text) {
                    Log.d("AuthTag", "Received message: ${frame.readText()}")

                }
            }

            return "success"
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}