package com.datn.apptravel.model

data class ScheduleActivity(
    val time: String,
    val title: String,
    val description: String,
    val iconResId: Int? = null
)