package com.example.logisticsdeliverymanager.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.logisticsdeliverymanager.data.Delivery
import com.example.logisticsdeliverymanager.data.DeliveryStatus
import com.example.logisticsdeliverymanager.viewmodel.LogisticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveriesScreen(
    viewModel: LogisticsViewModel,
    onDeliveryClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val deliveries by viewModel.deliveries.collectAsStateWithLifecycle()

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedStatus by rememberSaveable { mutableStateOf("ALL") }

    val filteredDeliveries = deliveries.filter { delivery ->
        val matchesSearch =
            delivery.trackingNumber.contains(searchQuery, ignoreCase = true) ||
                    delivery.recipientName.contains(searchQuery, ignoreCase = true) ||
                    delivery.address.contains(searchQuery, ignoreCase = true)

        val matchesStatus = when (selectedStatus) {
            "PENDING" -> delivery.status == DeliveryStatus.PENDING
            "IN_TRANSIT" -> delivery.status == DeliveryStatus.IN_TRANSIT
            "DELIVERED" -> delivery.status == DeliveryStatus.DELIVERED
            "CANCELLED" -> delivery.status == DeliveryStatus.CANCELLED
            else -> true
        }

        matchesSearch && matchesStatus
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deliveries") },
                navigationIcon = {
                    Text(
                        text = "‹",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .clickable { onBack() }
                            .padding(horizontal = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search deliveries") },
                placeholder = { Text("Tracking number, name or address") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStatus == "ALL",
                    onClick = { selectedStatus = "ALL" },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedStatus == "PENDING",
                    onClick = { selectedStatus = "PENDING" },
                    label = { Text("Pending") }
                )
                FilterChip(
                    selected = selectedStatus == "IN_TRANSIT",
                    onClick = { selectedStatus = "IN_TRANSIT" },
                    label = { Text("In transit") }
                )
                FilterChip(
                    selected = selectedStatus == "DELIVERED",
                    onClick = { selectedStatus = "DELIVERED" },
                    label = { Text("Delivered") }
                )
            }

            Text(
                text = "${filteredDeliveries.size} deliveries found",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (filteredDeliveries.isEmpty()) {
                Text(
                    text = "No deliveries found",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = filteredDeliveries,
                        key = { it.id }
                    ) { delivery ->
                        DeliveryCard(
                            delivery = delivery,
                            onClick = { onDeliveryClick(delivery.id) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun DeliveryCard(
    delivery: Delivery,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = delivery.trackingNumber,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = delivery.priority.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Text(
                text = delivery.recipientName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = delivery.address,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Status: ${delivery.status.name.replace("_", " ")}",
                style = MaterialTheme.typography.labelLarge,
                color = when (delivery.status) {
                    DeliveryStatus.DELIVERED ->
                        MaterialTheme.colorScheme.tertiary
                    DeliveryStatus.CANCELLED ->
                        MaterialTheme.colorScheme.error
                    DeliveryStatus.IN_TRANSIT ->
                        MaterialTheme.colorScheme.primary
                    DeliveryStatus.PENDING ->
                        MaterialTheme.colorScheme.secondary
                }
            )
        }
    }
}
