package com.shadow_shift_studio.travelagency_frontend.domain.use_case

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.domain.repository.ILoginRepository

class LoginUserUseCase(private val userRepository: ILoginRepository) {
    suspend fun execute(context: Context, login: String, password: String): Boolean {
        return userRepository.loginUser(context, login, password)
    }
}