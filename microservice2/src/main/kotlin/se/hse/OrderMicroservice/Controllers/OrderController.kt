package se.hse.OrderMicroservice.Controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import se.hse.OrderMicroservice.Application.dto.OrderDTO
import se.hse.OrderMicroservice.Application.dto.OrderInfoDTO
import se.hse.OrderMicroservice.Application.service.OrderService

@RestController
@RequestMapping("/order")
class OrderController(val orderService: OrderService) {

    @PostMapping("/create")
    fun createOrder(@RequestBody order: OrderDTO): ResponseEntity<String> {
        return try {
            val response = orderService.createOrder(order)
            ResponseEntity.ok(response)
        } catch (e:Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/info")
    fun getOrderInfo(@RequestBody info: OrderInfoDTO): ResponseEntity<String> {
        return try {
            val response = orderService.getOrderInfo(info)
            ResponseEntity.ok(response)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }

    }

}
