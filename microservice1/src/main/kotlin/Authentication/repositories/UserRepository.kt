package Authentication.repositories

import org.springframework.data.jpa.repository.JpaRepository
import Authentication.model.User

interface UserRepository: JpaRepository<User, Long> {

    fun findById(id: Int): User?
    fun findByEmail(email: String): User?
    fun findByNickname(nickname: String): User?
    fun findByEmailAndPassword(email: String, password: String): User?
}