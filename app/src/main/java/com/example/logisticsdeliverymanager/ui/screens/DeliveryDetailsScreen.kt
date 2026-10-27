package com.example.logisticsdeliverymanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.logisticsdeliverymanager.data.DeliveryStatus
import com.example.logisticsdeliverymanager.viewmodel.LogisticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryDetailsScreen(
    deliveryId: Int,
    viewModel: LogisticsViewModel,
    onBack: () -> Unit
) {
    val deliveries by viewModel.deliveries.collectAsStateWithLifecycle()
    val delivery = deliveries.find { it.id == deliveryId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Delivery Details") },
                navigationIcon = {
                    OutlinedButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (delivery == null) {
            Text(
                text = "Delivery not found",
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = delivery.trackingNumber,
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DetailRow("Recipient", delivery.recipientName)
                        DetailRow("Address", delivery.address)
                        DetailRow("Package", delivery.description)
                        DetailRow(
                            "Priority",
                            delivery.priority.name
                        )
                        DetailRow(
                            "Status",
                            delivery.status.name.replace("_", " ")
                        )
                    }
                }

                Text(
                    text = "Update delivery status",
                    style = MaterialTheme.typography.titleMedium
                )

                when (delivery.status) {
                    DeliveryStatus.PENDING -> {
                        Button(
                            onClick = {
                                viewModel.updateStatus(
                                    delivery.id,
                                    DeliveryStatus.IN_TRANSIT
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Mark as In Transit")
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.updateStatus(
                                    delivery.id,
                                    DeliveryStatus.CANCELLED
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancel Delivery")
                        }
                    }

                    DeliveryStatus.IN_TRANSIT -> {
                        Button(
                            onClick = {
                                viewModel.updateStatus(
                                    delivery.id,
                                    DeliveryStatus.DELIVERED
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Mark as Delivered")
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.updateStatus(
                                    delivery.id,
                                    DeliveryStatus.CANCELLED
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Cancel Delivery")
                        }
                    }

                    DeliveryStatus.DELIVERED -> {
                        Text("This delivery has been completed.")
                    }

                    DeliveryStatus.CANCELLED -> {
                        Text("This delivery has been cancelled.")
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
