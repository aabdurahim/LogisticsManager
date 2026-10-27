package com.example.logisticsdeliverymanager.data

class DeliveryRepository {

    fun getDeliveries(): List<Delivery> {
        return listOf(
            Delivery(
                id = 1,
                trackingNumber = "TRK-1001",
                recipientName = "Anna Smith",
                address = "Abay Avenue 10, Almaty",
                description = "Clothing package",
                priority = DeliveryPriority.HIGH,
                status = DeliveryStatus.IN_TRANSIT
            ),
            Delivery(
                id = 2,
                trackingNumber = "TRK-1002",
                recipientName = "Daniel Brown",
                address = "Dostyk Avenue 25, Almaty",
                description = "Electronics",
                priority = DeliveryPriority.NORMAL,
                status = DeliveryStatus.PENDING
            ),
            Delivery(
                id = 3,
                trackingNumber = "TRK-1003",
                recipientName = "Sophia Wilson",
                address = "Tole Bi Street 50, Almaty",
                description = "Books",
                priority = DeliveryPriority.LOW,
                status = DeliveryStatus.DELIVERED
            ),
            Delivery(
                id = 4,
                trackingNumber = "TRK-1004",
                recipientName = "Michael Davis",
                address = "Satpayev Street 15, Almaty",
                description = "Documents",
                priority = DeliveryPriority.HIGH,
                status = DeliveryStatus.PENDING
            ),
            Delivery(
                id = 5,
                trackingNumber = "TRK-1005",
                recipientName = "Emma Taylor",
                address = "Zheltoksan Street 30, Almaty",
                description = "Household goods",
                priority = DeliveryPriority.NORMAL,
                status = DeliveryStatus.IN_TRANSIT
            )
        )
    }
}
