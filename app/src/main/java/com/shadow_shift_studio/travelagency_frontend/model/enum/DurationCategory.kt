package com.shadow_shift_studio.travelagency_frontend.model.enum

enum class DurationCategory {
    ONE_TO_TWO_DAYS,
    THREE_TO_FIVE_DAYS,
    SEVEN_TO_TEN_DAYS,
    ELEVEN_FOURTEEN_DAYS
}

fun DurationCategory.toFormattedDouble(): List<Double> {
    return when (this) {
        DurationCategory.ONE_TO_TWO_DAYS -> listOf(1.0, 1.5, 2.0)
        DurationCategory.THREE_TO_FIVE_DAYS -> listOf(3.0, 3.5, 4.0, 4.5, 5.0)
        DurationCategory.SEVEN_TO_TEN_DAYS -> listOf(7.0, 7.5, 8.0, 8.5, 9.0, 9.5, 10.0)
        DurationCategory.ELEVEN_FOURTEEN_DAYS -> listOf(11.0, 11.5, 12.0, 12.5, 13.0, 13.5, 14.0)
    }
}