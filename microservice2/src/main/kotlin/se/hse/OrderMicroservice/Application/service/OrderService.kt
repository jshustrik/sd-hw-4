package se.hse.OrderMicroservice.Application.service

import jakarta.transaction.Transactional
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.springframework.stereotype.Service
import se.hse.OrderMicroservice.Application.dto.OrderDTO
import se.hse.OrderMicroservice.Application.dto.OrderInfoDTO
import se.hse.OrderMicroservice.Application.dto.StationDTO
import se.hse.OrderMicroservice.Application.repositories.OrderRepository
import se.hse.OrderMicroservice.Application.repositories.StationRepository
import se.hse.OrderMicroservice.Application.utils.*
import se.hse.OrderMicroservice.Entity.Order
import se.hse.OrderMicroservice.Entity.Station


@Service
class OrderService(private val orderRepository: OrderRepository, private val stationRepository: StationRepository) {

    @Transactional
    fun createOrder(order: OrderDTO): String{
        if (!userAuthValidate(order.userId)) {
            throw IllegalAccessException("User is not log in")
        }
        if (order.status < 1 || order.status > 3){
            throw IllegalAccessException("Incorrect order status format")
        }
        if (stationRepository.findById(order.fromStationId) == null){
            throw IllegalAccessException("Wrong origin station id")
        }
        if (stationRepository.findById(order.toStationId) == null){
            throw IllegalAccessException("Wrong destination station id")
        }
        orderRepository.saveAndFlush(Order(userId = order.userId, fromStationId = order.fromStationId, toStationId = order.toStationId, status = order.status))
        return "The order created"
    }

    @Transactional
    fun createStation(station : StationDTO): String {
        if (!stationValidate(station.station)) {
            throw IllegalAccessException("Station name must have at least one letter")
        }
        stationRepository.saveAndFlush(Station(station = station.station))
        return "The station created"
    }

    fun getOrderInfo(info: OrderInfoDTO): String {
        val order = orderRepository.findById(info.id) ?: throw IllegalAccessException("There is no order with this id")
        return "Order with id ${order.id} was created ${order.created} at station ${order.fromStationId} and send to station ${order.toStationId} with status ${order.status}"
    }

}