package Authentication.repositories

import org.springframework.data.jpa.repository.JpaRepository
import Authentication.model.Session
import java.sql.Timestamp

interface SessionRepository: JpaRepository<Session, Long> {

    fun findByUserId(userId: Int): Session?
    fun findByToken(token: String): Session?

    fun findByExpiresLessThan(timestamp: Timestamp): List<Session>

    fun deleteById(id: Int): Long
}