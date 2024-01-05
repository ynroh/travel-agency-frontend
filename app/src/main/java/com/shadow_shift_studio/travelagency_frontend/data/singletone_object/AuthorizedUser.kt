package com.shadow_shift_studio.travelagency_frontend.data.singletone_object

import com.shadow_shift_studio.travelagency_frontend.model.entity.User

object AuthorizedUser {
    var login = ""
    var id: Long = 0
    var user: User? = null
    var  isPassportSaved: Boolean = true
}