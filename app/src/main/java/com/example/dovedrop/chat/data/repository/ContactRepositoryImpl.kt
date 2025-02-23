package com.example.dovedrop.chat.data.repository

import android.util.Log
import com.example.dovedrop.chat.data.model.user.AddContactError
import com.example.dovedrop.chat.data.model.user.ContactDTO
import com.example.dovedrop.chat.data.model.user.GetContactsError
import com.example.dovedrop.chat.data.network.dto.auth.response.ApiResponse
import com.example.dovedrop.chat.domain.network.ContactRepository
import com.example.dovedrop.chat.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsBytes
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class ContactRepositoryImpl(
    private val httpClient: HttpClient,
    private val encryptedPrefs: EncryptedPrefs
): ContactRepository {
    override suspend fun getAllContacts(): Result<List<ContactDTO>, GetContactsError> {
        Log.d("AuthTag", "getAllContacts() in repo called")
       return try {
            val response = httpClient
                .get("/user/getAllContacts") {
                    url {
                        header(HttpHeaders.Authorization, "Bearer " + encryptedPrefs.getToken())
                    }
                }
                .body<ApiResponse<List<ContactDTO>>>()

            if(response.status == "Success") {
                Log.d("AuthTag", response.status)
                return Result.Success(data = response.data ?: emptyList())
            } else {
                Log.d("AuthTag", "Error in contactRepo: ${response.status} and ${response.message}")
                return when(response.message) {
                    GetContactsError.UNAUTHORIZED.name -> {
                        Result.Error(GetContactsError.UNAUTHORIZED)
                    }
                    GetContactsError.SERVER_ERROR.name -> {
                        Result.Error(GetContactsError.SERVER_ERROR)
                    }
                    else -> {
                        Result.Error(GetContactsError.UNKNOWN_ERROR)
                    }
                }
            }
       } catch (e: Exception) {
           Log.d("AuthTag", "Error getting all contacts: ${e.message}")
           Result.Error(GetContactsError.UNKNOWN_ERROR)
        }
    }

    override suspend fun addContact(name: String, email: String): Result<ContactDTO?, AddContactError> {
        return try {
            val response = httpClient
                .post("/user/addContact") {
                    header(HttpHeaders.Authorization, "Bearer " + encryptedPrefs.getToken())
                    contentType(ContentType.Application.Json)
                    setBody(
                        mapOf(
                            "p2Name" to name,
                            "p2Email" to email
                        )
                    )
                }
                .body<ApiResponse<ContactDTO>>()

            if(response.status == "Success") {
                return Result.Success(response.data)
            } else {
                return when(response.message) {
                    AddContactError.UNAUTHORIZED.name -> {
                        Result.Error(AddContactError.UNAUTHORIZED)
                    }
                    AddContactError.INVALID_DATA_FORMAT.name -> {
                        Result.Error(AddContactError.INVALID_DATA_FORMAT)
                    }
                    AddContactError.CONTACT_ALREADY_EXISTS.name -> {
                        Result.Error(AddContactError.CONTACT_ALREADY_EXISTS)
                    }
                    AddContactError.SERVER_ERROR.name -> {
                        Result.Error(AddContactError.SERVER_ERROR)
                    }
                    else -> {
                        Result.Error(AddContactError.UNKNOWN_ERROR)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("AuthTag", "Error adding contact: ${e.message}")
            Result.Error(AddContactError.UNKNOWN_ERROR)
        }
    }
}