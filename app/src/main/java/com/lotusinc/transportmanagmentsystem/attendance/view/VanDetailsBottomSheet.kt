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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lotusinc.transportmanagmentsystem.attendance.model.Van

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VanDetailsBottomSheet(
    van: Van,
    onMarkAttendance: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Van Details", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(text = "Van Number: ${van.number}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Driver Name: ${van.driver}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Route:", style = MaterialTheme.typography.bodySmall)
            van.route.forEach { location ->
                Text(text = "- $location", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onMarkAttendance,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Mark Attendance")
            }
        }
    }
}
