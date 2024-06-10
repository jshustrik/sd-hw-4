package se.hse.OrderMicroservice.Application.utils

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import se.hse.OrderMicroservice.Application.repositories.OrderRepository
import se.hse.OrderMicroservice.Application.repositories.StationRepository
import se.hse.OrderMicroservice.Entity.Order
import kotlin.random.Random

@Component
class HandleOrder(private val orderRepository: OrderRepository) {

    @Scheduled(fixedDelay = 10000) // 10 second delay
    private fun processOrdersWithDelay() {
        val ordersToProcess = orderRepository.findAllByStatus(1)
        ordersToProcess.forEach { order ->
            val newStatus = if (Random.nextBoolean()) 2 else 3
            order.status = newStatus
            order.id?.let { orderRepository.updateOrderStatus(it, order.status) }
        }
    }
}