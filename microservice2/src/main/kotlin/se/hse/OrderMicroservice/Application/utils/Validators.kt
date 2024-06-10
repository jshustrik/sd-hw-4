package se.hse.OrderMicroservice.Application.utils

import khttp.responses.Response
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients

fun stationValidate(station: String): Boolean {
    return station.isNotEmpty() &&
            (station.contains(Regex("[A-Z]")) || station.contains(Regex("[a-z]")))
}

fun userAuthValidate (id: Int) : Boolean {
    val jsonBody = "{\"id\": \"${id}\"}"
    val httpClient = HttpClients.createDefault()
    val request = HttpPost("http://microservice1:8080/user/check")
    request.addHeader("Content-Type", "application/json")
    request.entity = StringEntity(jsonBody)
    val response = httpClient.execute(request)
//    val response : Response = khttp.post(
//        url = "http://microservice1:8080/user/check",
//        json = mapOf("id" to id))
    return response.statusLine.statusCode == 200
    //println(response.entity.content.bufferedReader().use { it.readText() })
}