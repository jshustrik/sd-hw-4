package se.hse.OrderMicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class OrderMicroserviceApplication

fun main(args: Array<String>) {
	runApplication<OrderMicroserviceApplication>(*args)
}
