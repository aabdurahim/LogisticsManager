package com.example.logisticsdeliverymanager.data

data class Delivery(
    val id: Int,
    val trackingNumber: String,
    val recipientName: String,
    val address: String,
    val description: String,
    val priority: DeliveryPriority,
    val status: DeliveryStatus
)

enum class DeliveryStatus {
    PENDING,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}

enum class DeliveryPriority {
    LOW,
    NORMAL,
    HIGH
}
