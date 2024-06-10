package se.hse.OrderMicroservice.Controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.hse.OrderMicroservice.Application.dto.OrderDTO
import se.hse.OrderMicroservice.Application.dto.StationDTO
import se.hse.OrderMicroservice.Application.service.OrderService

@RestController
@RequestMapping("/station")
class StationController(val orderService: OrderService) {

    @PostMapping("/create")
    fun createOrder(@RequestBody station: StationDTO): ResponseEntity<String> {
        return try {
            val response = orderService.createStation(station)
            ResponseEntity.ok(response)
        } catch (e:Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}