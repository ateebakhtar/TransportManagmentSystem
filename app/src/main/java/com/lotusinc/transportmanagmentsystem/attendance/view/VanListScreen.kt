package com.lotusinc.transportmanagmentsystem.attendance.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun VanListScreen(
    vans: List<Van>,
    onVanSelected: (Van) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(vans) { van ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onVanSelected(van) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Van Number: ${van.number}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Driver: ${van.driver}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}