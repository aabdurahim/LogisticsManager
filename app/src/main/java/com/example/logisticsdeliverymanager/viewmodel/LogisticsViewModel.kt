package com.example.logisticsdeliverymanager.viewmodel

import androidx.lifecycle.ViewModel
import com.example.logisticsdeliverymanager.data.Delivery
import com.example.logisticsdeliverymanager.data.DeliveryPriority
import com.example.logisticsdeliverymanager.data.DeliveryRepository
import com.example.logisticsdeliverymanager.data.DeliveryStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LogisticsViewModel : ViewModel() {

    private val repository = DeliveryRepository()

    private val _deliveries = MutableStateFlow(repository.getDeliveries())
    val deliveries: StateFlow<List<Delivery>> = _deliveries.asStateFlow()

    fun addDelivery(
        recipientName: String,
        address: String,
        description: String,
        priority: DeliveryPriority
    ) {
        val nextId = (_deliveries.value.maxOfOrNull { it.id } ?: 0) + 1

        val newDelivery = Delivery(
            id = nextId,
            trackingNumber = "TRK-${1000 + nextId}",
            recipientName = recipientName,
            address = address,
            description = description,
            priority = priority,
            status = DeliveryStatus.PENDING
        )

        _deliveries.update { current ->
            current + newDelivery
        }
    }

    fun updateStatus(id: Int, newStatus: DeliveryStatus) {
        _deliveries.update { current ->
            current.map { delivery ->
                if (delivery.id == id) {
                    delivery.copy(status = newStatus)
                } else {
                    delivery
                }
            }
        }
    }

    fun searchDeliveries(query: String): List<Delivery> {
        return _deliveries.value.filter { delivery ->
            delivery.trackingNumber.contains(query, ignoreCase = true) ||
                    delivery.recipientName.contains(query, ignoreCase = true) ||
                    delivery.address.contains(query, ignoreCase = true)
        }
    }

    fun filterByStatus(status: DeliveryStatus?): List<Delivery> {
        return if (status == null) {
            _deliveries.value
        } else {
            _deliveries.value.filter { it.status == status }
        }
    }
}
