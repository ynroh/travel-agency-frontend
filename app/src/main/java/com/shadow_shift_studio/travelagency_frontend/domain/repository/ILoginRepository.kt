package com.shadow_shift_studio.travelagency_frontend.domain.repository

import android.content.Context

interface ILoginRepository {
    suspend fun loginUser(context: Context, login: String, password: String): Boolean
}