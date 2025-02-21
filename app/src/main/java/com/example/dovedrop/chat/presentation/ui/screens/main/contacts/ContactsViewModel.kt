package com.example.dovedrop.chat.presentation.ui.screens.main.contacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImagePainter
import com.example.dovedrop.chat.data.model.user.AddContactError
import com.example.dovedrop.chat.data.model.user.ContactDTO
import com.example.dovedrop.chat.domain.network.ContactRepository
import com.example.dovedrop.chat.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val contactRepository: ContactRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(ContactUIState())
    val uiState = _uiState.asStateFlow()

    private val _toastChannel = Channel<String>()
    val toastFlow = _toastChannel.receiveAsFlow()

    fun getAllContacts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val response = contactRepository.getAllContacts()
            when(response) {
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _toastChannel.send("Unable to sync the latest contacts!")
                    Log.d("AuthTag", "In viewmodel: error response ${response.error}")
                }
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            contacts = response.data
                        )
                    }
                }
            }
        }
    }

    fun addContact() {
        viewModelScope.launch {
            _uiState.update { it.copy(isBottomSheetVisible = false, isLoading = true) }
            val response = contactRepository.addContact(
                name = uiState.value.contactName,
                email = uiState.value.contactEmail
            )
            when(response) {
                is Result.Success -> {
                    _uiState.update { it.copy(isLoading = false, contactEmail = "", contactName = "") }
                    _toastChannel.send("Contact added successfully.")
                    getAllContacts()
                }
                is Result.Error -> {
                    _uiState.update { it.copy(isLoading = false, contactName = "", contactEmail = "") }
                    when(response.error) {
                        AddContactError.CONTACT_ALREADY_EXISTS -> {
                            _toastChannel.send("Contact already exists with same email address.")
                        }
                        AddContactError.UNKNOWN_ERROR -> {
                            _toastChannel.send("OOPS, Something went wrong!")
                        }
                        AddContactError.SERVER_ERROR -> {
                            _toastChannel.send("Please try after sometime!")
                        }
                        AddContactError.UNAUTHORIZED -> {
                            _toastChannel.send("Login again")
                        }
                        AddContactError.INVALID_DATA_FORMAT -> {
                            _toastChannel.send("Please enter valid data!")
                        }
                    }
                }

            }
        }
    }

    //Functions for updating UI
    fun updateSheetVisibility() {
        _uiState.update { it.copy(isBottomSheetVisible = !uiState.value.isBottomSheetVisible) }
    }
    fun updateInputName(name: String) {
        _uiState.update { it.copy(contactName = name) }
    }
    fun updateInputEmail(email: String) {
        _uiState.update { it.copy(contactEmail = email) }
    }
    fun clearEntries() {
        _uiState.update { it.copy(contactEmail = "", contactName = "") }
    }
}

data class ContactUIState(
    val isLoading: Boolean = false,
    val contacts: List<ContactDTO>? = null,
    val isBottomSheetVisible: Boolean = false,
    val contactName: String = "",
    val contactEmail: String = ""
)