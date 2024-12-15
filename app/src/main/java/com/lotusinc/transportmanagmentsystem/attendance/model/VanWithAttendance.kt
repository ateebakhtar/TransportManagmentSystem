package com.lotusinc.transportmanagmentsystem.attendance.model

data class VanWithAttendance(
    val van: Van,
    val attendanceCount: Int,
    val studentNames: List<String>
)