package com.shadow_shift_studio.travelagency_frontend.domain.use_case

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.domain.repository.IRegistrationRepository


class RegisterUserUseCase(private val userRepository: IRegistrationRepository) {
    suspend fun userRegister(context: Context, login: String, email: String, password: String): Boolean {
        return userRepository.registerUser(context, login, email, password)
    }
}