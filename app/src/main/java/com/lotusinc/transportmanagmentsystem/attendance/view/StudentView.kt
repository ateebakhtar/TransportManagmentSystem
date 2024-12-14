package com.lotusinc.transportmanagmentsystem.attendance.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.lotusinc.transportmanagmentsystem.attendance.model.Van

@Composable
fun StudentView(vans: List<Van>, onMarkAttendance: (Van) -> Unit) {
    var selectedVan by remember { mutableStateOf<Van?>(null) }

    Scaffold {
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            VanListScreen(
                vans = vans,
                onVanSelected = { van ->
                    selectedVan = van
                }
            )
        }

        selectedVan?.let { van ->
            VanDetailsBottomSheet(
                van = van,
                onMarkAttendance = {
                    onMarkAttendance(van) // Mark attendance logic here
                    selectedVan = null // Close the bottom sheet
                },
                onDismiss = {
                    selectedVan = null
                }
            )
        }
    }
}
