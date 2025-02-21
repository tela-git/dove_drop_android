package com.example.dovedrop.chat.domain.network

import com.example.dovedrop.chat.data.model.user.AddContactError
import com.example.dovedrop.chat.data.model.user.ContactDTO
import com.example.dovedrop.chat.data.model.user.GetContactsError
import com.example.dovedrop.chat.domain.util.Result

interface ContactRepository {
    suspend fun getAllContacts(): Result<List<ContactDTO>, GetContactsError>
    suspend fun addContact(name: String, email: String): Result<ContactDTO?, AddContactError>
}