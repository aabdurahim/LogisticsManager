package com.example.logisticsdeliverymanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.logisticsdeliverymanager.data.DeliveryStatus
import com.example.logisticsdeliverymanager.viewmodel.LogisticsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: LogisticsViewModel,
    onOpenDeliveries: () -> Unit,
    onAddDelivery: () -> Unit
) {
    val deliveries by viewModel.deliveries.collectAsStateWithLifecycle()

    val total = deliveries.size
    val pending = deliveries.count { it.status == DeliveryStatus.PENDING }
    val inTransit = deliveries.count { it.status == DeliveryStatus.IN_TRANSIT }
    val delivered = deliveries.count { it.status == DeliveryStatus.DELIVERED }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Logistics Manager") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Welcome back!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Here is your delivery overview.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatisticCard(
                        title = "Total",
                        value = total.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatisticCard(
                        title = "Pending",
                        value = pending.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatisticCard(
                        title = "In Transit",
                        value = inTransit.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatisticCard(
                        title = "Delivered",
                        value = delivered.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onOpenDeliveries,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View All Deliveries")
                }

                OutlinedButton(
                    onClick = onAddDelivery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text("＋ Add New Delivery")
                }
            }
        }
    }
}


@Composable
private fun StatisticCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                androidx.compose.foundation.layout.Box(
                    modifier = Modifier
                        .height(4.dp)
                        .weight(1f)
                        .padding(end = 12.dp)
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 4.dp)
                    ) {
                        androidx.compose.material3.HorizontalDivider(
                            color = MaterialTheme.colorScheme.secondary,
                            thickness = 4.dp
                        )
                    }
                }
            }
        }
    }
}

