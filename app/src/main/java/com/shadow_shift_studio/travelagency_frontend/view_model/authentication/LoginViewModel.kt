package com.shadow_shift_studio.travelagency_frontend.view_model.authentication

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context): ViewModel() {

    var login: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")

    suspend fun loginUser() {
    }
}