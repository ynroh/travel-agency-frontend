package com.shadow_shift_studio.travelagency_frontend.data.singletone_object

import androidx.compose.runtime.mutableStateOf

object Navbar {
    private val isNavbarVisible = mutableStateOf(true)

    fun setNavbarVisible(index: Boolean) {
        isNavbarVisible.value = index
    }

    fun getNavbarVisible(): Boolean {
        return isNavbarVisible.value
    }
}