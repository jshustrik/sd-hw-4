package se.hse.OrderMicroservice.Application.repositories

import org.springframework.data.jpa.repository.JpaRepository
import se.hse.OrderMicroservice.Entity.Station

interface StationRepository: JpaRepository<Station, Long> {
    fun findById(stationId: Int): Station?
}