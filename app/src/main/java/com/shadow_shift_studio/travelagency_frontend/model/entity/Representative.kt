package com.shadow_shift_studio.travelagency_frontend.model.entity

data class Representative(
    var  id: Int,
    var last_name: String,
    var name: String,
    var middle_name: String,
    var description: String,
    var city: City,
)