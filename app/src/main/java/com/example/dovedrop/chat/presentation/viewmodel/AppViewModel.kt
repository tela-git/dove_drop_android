package com.example.dovedrop.chat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.chat.domain.model.ContactDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val supabase: SupabaseClient
): ViewModel() {
    private val _contactListUiState = MutableStateFlow(ContactsList())
    val contactListUiState = _contactListUiState.asStateFlow()

    @OptIn(SupabaseExperimental::class)
    fun getContacts() {
        viewModelScope.launch {
            supabase
                .from("contacts")
                .selectAsFlow(ContactDTO::id)
                .collect {contactsList->
                    _contactListUiState.update { state->
                        state.copy(
                            contacts = contactsList.map {
                                UiContact(
                                    name = it.name,
                                    email = it.email
                                )
                            }
                        )
                    }
                }
        }
    }
}


data class ContactsList(
    val contacts: List<UiContact> = listOf()
)
data class UiContact(
    val name: String = "",
    val email: String = ""
)