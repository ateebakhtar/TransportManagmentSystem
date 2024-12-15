package com.lotusinc.transportmanagmentsystem.attendance.model

data class VanAttendance(
    val vanId: String,
    val count: Int,
    val listOfStudents: List<String>
)
