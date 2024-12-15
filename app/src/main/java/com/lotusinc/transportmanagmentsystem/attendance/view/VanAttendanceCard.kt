package com.lotusinc.transportmanagmentsystem.attendance.view


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lotusinc.transportmanagmentsystem.attendance.model.Van
import com.lotusinc.transportmanagmentsystem.attendance.model.VanWithAttendance

@Composable
fun VanAttendanceCard(vanWithAttendance: VanWithAttendance) {
    val van = vanWithAttendance.van

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Van Number: ${van.number}", style = MaterialTheme.typography.bodySmall)
            Text("Driver: ${van.driver}", style = MaterialTheme.typography.bodyMedium)
            Text("Route: ${van.route.joinToString(", ")}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Students Marked Attendance: ${vanWithAttendance.attendanceCount}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            if (vanWithAttendance.studentNames.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Students:", style = MaterialTheme.typography.labelMedium)
                vanWithAttendance.studentNames.forEach { studentName ->
                    Text("- $studentName", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
