package com.shadow_shift_studio.travelagency_frontend.domain.repository

import android.content.Context

interface IRegistrationRepository {
    suspend fun registerUser(context: Context, login: String, email: String, password: String): Boolean
}