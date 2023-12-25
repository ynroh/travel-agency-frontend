package com.shadow_shift_studio.travelagency_frontend.data.singletone_object

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.shadow_shift_studio.travelagency_frontend.model.enum.DurationCategory
import java.time.Duration

object Filter {
    val selectedCountries = mutableStateListOf<String>()
    var selectedMinPrice = mutableStateOf<Int>(0)
    var selectedMaxPrice = mutableStateOf<Int>(0)
    val selectedDuration = mutableStateListOf<DurationCategory>()
    val selectedDurationToDouble = mutableStateListOf<Double>()

    fun resetAllSelected() {
        selectedCountries.clear()
        selectedMinPrice.value = 0
        selectedMaxPrice.value = 0
        selectedDuration.clear()
    }
}