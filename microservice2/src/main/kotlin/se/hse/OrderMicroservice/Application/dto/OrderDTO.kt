package se.hse.OrderMicroservice.Application.dto

data class OrderDTO (

    var userId: Int,

    var fromStationId: Int,

    var toStationId: Int,

    var status: Int
)