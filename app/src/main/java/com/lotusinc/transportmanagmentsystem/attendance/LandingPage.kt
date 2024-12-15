package com.lotusinc.transportmanagmentsystem.attendance

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lotusinc.transportmanagmentsystem.attendance.model.Van
import com.lotusinc.transportmanagmentsystem.attendance.model.VanWithAttendance
import com.lotusinc.transportmanagmentsystem.attendance.view.StudentView
import com.lotusinc.transportmanagmentsystem.attendance.view.VanAttendanceCard
import com.lotusinc.transportmanagmentsystem.ui.theme.TransportManagmentSystemTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LandingPage(
    name: String,
    modifier: Modifier = Modifier,
    context: Context
) {

    val attendanceViewModel: AttendanceViewModel = viewModel()
    val vans by attendanceViewModel.vans.collectAsState()

    when (name) {
        "COORDINATOR" -> {
            var vanAttendance by remember { mutableStateOf(mutableListOf<VanWithAttendance>()) }
            Column {
                attendanceViewModel.fetchVansAndAttendance { vans ->
                    vanAttendance = vans.toMutableList()
                }
                vanAttendance.forEach { van ->
                    VanAttendanceCard(van)
                }
            }
        }

        "STUDENT" -> {
            Column {
                var isAttendanceMarked by remember { mutableStateOf(false) }
                attendanceViewModel.checkAttendance {
                    isAttendanceMarked = it
                }
                if (isAttendanceMarked) {
                    Text(
                        text = "You have already marked your attendance today.",
                        modifier = modifier.padding(24.dp)
                    )
                } else {
                    StudentView(vans) {

                        val date =
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                        attendanceViewModel.markAttendance(it.number, date) { success ->
                            if (success) {
                                Toast.makeText(context, "Attendance marked!", Toast.LENGTH_SHORT)
                                    .show()
                                isAttendanceMarked = true
                            } else {
                                Toast.makeText(
                                    context,
                                    "Failed to mark attendance.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}