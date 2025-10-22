package com.datn.apptravel.model

data class ScheduleDay(
    val dayNumber: Int,
    val title: String,
    val date: String,
    val activities: List<ScheduleActivity> = emptyList()
)