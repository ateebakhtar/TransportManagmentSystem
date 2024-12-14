package com.lotusinc.transportmanagmentsystem.attendance.model

data class Van(
    val driver: String,
    val number: String,
    val route: List<String>
)