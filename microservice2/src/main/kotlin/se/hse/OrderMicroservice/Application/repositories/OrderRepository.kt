package se.hse.OrderMicroservice.Application.repositories

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import se.hse.OrderMicroservice.Entity.Order

interface OrderRepository: JpaRepository<Order, Long> {
    fun findById(orderId: Int): Order?
    fun findAllByStatus(status: Int): MutableList<Order>

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = :newStatus WHERE o.id = :orderId")
    fun updateOrderStatus(orderId: Int, newStatus: Int)
}