package com.shadow_shift_studio.travelagency_frontend.domain.repository

import android.content.Context
import com.shadow_shift_studio.travelagency_frontend.model.entity.Country

interface ICountyRepository {
    suspend fun getCountries(context: Context): List<Country>
}