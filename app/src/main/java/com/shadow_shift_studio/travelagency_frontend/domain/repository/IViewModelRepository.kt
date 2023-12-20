package com.shadow_shift_studio.travelagency_frontend.domain.repository

interface IViewModelRepository {
    fun removeTrailingSpaces(input: String): String
}