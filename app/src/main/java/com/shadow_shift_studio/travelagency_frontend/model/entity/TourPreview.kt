package com.shadow_shift_studio.travelagency_frontend.model.entity

import java.math.BigDecimal
import java.text.DecimalFormat

data class TourPreview (
    var id: Long,
    var title: String,
    var stayDuration: Double,
    var cost: BigDecimal,
    var photosUrl: String,
    var country: Country
)