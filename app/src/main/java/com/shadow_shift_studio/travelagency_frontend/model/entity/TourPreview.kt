package com.shadow_shift_studio.travelagency_frontend.model.entity

import java.math.BigDecimal

data class TourPreview (
    var id: Long?,
    var title: String?,
    var stayDuration: Double?,
    var cost: BigDecimal?,
    var photosUrl: List<String>?,
    var country: Country?,
    var representative: Representative?,
    var routePoints: List<RoutePoint>?
)