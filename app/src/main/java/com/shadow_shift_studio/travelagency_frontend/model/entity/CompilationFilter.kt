package com.shadow_shift_studio.travelagency_frontend.model.entity

import androidx.compose.runtime.mutableStateListOf
import com.shadow_shift_studio.travelagency_frontend.model.enum.DurationCategory

class CompilationFilter {
    var selectedCountries = mutableStateListOf<String>()
    var selectedDuration = mutableStateListOf<DurationCategory>()
    var selectedDurationToDouble = mutableStateListOf<Double>()

    fun resetAllSelected() {
        selectedCountries.clear()
        selectedDuration.clear()
    }
}