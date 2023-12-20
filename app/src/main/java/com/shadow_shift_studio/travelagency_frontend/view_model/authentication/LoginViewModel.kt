package com.shadow_shift_studio.travelagency_frontend.view_model.authentication

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shadow_shift_studio.travelagency_frontend.data.api_request.UserAuthorizationRequest
import com.shadow_shift_studio.travelagency_frontend.data.singletone_object.AuthorizedUser
import com.shadow_shift_studio.travelagency_frontend.domain.use_case.LoginUserUseCase
import com.shadow_shift_studio.travelagency_frontend.domain.use_case.ViewModelUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context): ViewModel() {

    var login: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")

    val loginStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private val loginUserUseCase: LoginUserUseCase =
        LoginUserUseCase(UserAuthorizationRequest())
    private val vmUserUseCase: ViewModelUseCase =
        ViewModelUseCase()

    suspend fun loginUser() {
        viewModelScope.launch {
            var login = vmUserUseCase.removeTrailingSpaces(login.value)
            var password = vmUserUseCase.removeTrailingSpaces(password.value)

            val status = loginUserUseCase.execute(context, login, password)
            loginStatusLiveData.value = status

            if(status) {
                AuthorizedUser.login = login
            }
        }.join()
    }
}