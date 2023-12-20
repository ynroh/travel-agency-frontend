package com.shadow_shift_studio.travelagency_frontend.model.entity

import java.util.Date

data class User(
    var id: Long,
    var phoneNumber: String,
    var email: String,
    var login: String,
    var password: String,
    var photoUrl: String
)