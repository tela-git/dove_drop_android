package com.example.dovedrop.chat.presentation.ui.screens.auth.t_and_c

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dovedrop.chat.data.network.dto.auth.response.TermsAndConditionsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

const val INTERNET_CHECK = "InternetCheck"

class TAndCViewModel(
    private val httpClient: HttpClient
): ViewModel() {
    private val _uiState = MutableStateFlow(TermsAndConditionsState())
    val uiState = _uiState.asStateFlow()

    fun fetchTAndC() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(loading = true) }
                val response =  httpClient.get("/ag/tAndC").body<TermsAndConditionsResponse>()
                Log.d(INTERNET_CHECK, response.termsAndConditions)
                _uiState.update { state->
                    state.copy(
                        termsAndConditions = response.termsAndConditions,
                        loading = false
                    )
                }
            } catch (e: HttpRequestTimeoutException) {
                _uiState.update { state->
                    state.copy(
                        loading = false,
                        error = "Request Time Out"
                    )
                }
            }
            catch (e: Exception) {
               Log.e(INTERNET_CHECK, "Message: ${e.message} \n LocalizedMessage: ${e.localizedMessage}")
                _uiState.update { state->
                    state.copy(
                        loading = false,
                        error = "Error: " + e.localizedMessage
                    )
                }
            }
        }
    }

    init {
        fetchTAndC()
    }
}

@Serializable
data class TermsAndConditionsState(
    val loading: Boolean = false,
    val termsAndConditions: String = "",
    val error: String? = null
)