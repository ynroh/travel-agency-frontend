package com.shadow_shift_studio.travelagency_frontend.model.entity

data class RoutePoint (
    var id: Int,
    var title: String,
    var stayDuration: Double,
    var hotels: Hotel,
    var city: City,
    var excursions: Excursion,
)