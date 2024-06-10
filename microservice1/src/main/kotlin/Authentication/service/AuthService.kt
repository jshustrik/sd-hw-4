package Authentication.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import Authentication.dto.LoginDTO
import Authentication.dto.SessionDTO
import Authentication.dto.UserDTO
import Authentication.repositories.SessionRepository
import Authentication.repositories.UserRepository
import Authentication.utils.emailValidate
import Authentication.utils.hashPassword
import Authentication.utils.nicknameValidate
import Authentication.utils.passwordValidate
import Authentication.model.Session
import Authentication.model.User
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class AuthService(private val userRepository: UserRepository, private  val sessionRepository: SessionRepository) {
    @Transactional
    fun registerUser(user: UserDTO): String{
        if (!emailValidate(user.email)){
            throw IllegalAccessException("Incorrect Email format")
        }
        if (!passwordValidate(user.password) ){
            throw IllegalAccessException("Incorrect password.\n Password must have at least eight characters, including letters of both cases, digits and special characters like (!@#$%^&*+.,-) and have no spaces")
        }
        if (!nicknameValidate(user.nickname)) {
            throw IllegalAccessException("The user nickname must contain at least one letter or number")
        }
        if (userRepository.findByEmail(user.email) != null){
            throw IllegalAccessException("This email is already taken. Please log in")
        }
        else if (userRepository.findByNickname(user.nickname) != null){
            throw IllegalAccessException("This nickname is already taken.")
        }
        userRepository.saveAndFlush(User(nickname = user.nickname.trim(), email = user.email, password = hashPassword(user.password)))
        return "The user is registered"
    }

    @Transactional
    fun login(user: LoginDTO): String {
        removeExpiredTokens()
        if (!emailValidate(user.email)){
            throw IllegalAccessException("Incorrect Email format")
        }
        if (!passwordValidate(user.password) ){
            throw IllegalAccessException("Incorrect password.\n" +
                    " Password must have at least eight characters, including letters of both cases, digits and special characters like (!@#\$%^&*+.,-) and have no spaces")
        }
        userRepository.findByEmail(user.email)?.id
            ?: throw IllegalAccessException("There is no user with this email")

        val userId = userRepository.findByEmailAndPassword(user.email, hashPassword(user.password))?.id
            ?: throw IllegalAccessException("Wrong password")
        val sessionStatus: Session? = sessionRepository.findByUserId(userId)
        if (sessionStatus != null) {
            throw IllegalAccessException("The user is already logged in")
        }
        var userToken: String
        do {
            userToken = getToken(userId)
        } while (sessionRepository.findByToken(userToken) != null)
        val date: LocalDateTime = LocalDateTime.now().plusDays(30)
        sessionRepository.save(Session(token = userToken, expires = Timestamp.valueOf(date), userId = userId))
        return "{\"token\": $userToken}"
    }

    @Transactional
    fun logout(sessionDto: SessionDTO): String{
        removeExpiredTokens()
        val session = sessionRepository.findByToken(sessionDto.token) ?: throw IllegalAccessException("User isn't logged in")
        session.id?.let { sessionRepository.deleteById(it) }
        return "User is logged out"
    }

    fun getUserInfo(sessionDto: SessionDTO): String{
        removeExpiredTokens()
        val session = sessionRepository.findByToken(sessionDto.token) ?: throw IllegalAccessException("User isn't logged in")
        val user = userRepository.findById(session.userId) ?: throw IllegalAccessException("User isn't logged in")
        return "{\"id\" : ${user.id}\n \"email\" : ${user.email}\n" +
                " \"nickname\" : ${user.nickname}}"
    }

    fun checkUser(id: Int): String{
        removeExpiredTokens()
        val user = userRepository.findById(id) ?: throw IllegalAccessException("User isn't logged in")
        val session = user.id?.let { sessionRepository.findByUserId(it) } ?: throw IllegalAccessException("User isn't logged in")
        return session.token
    }

    fun removeExpiredTokens(){
        val date: LocalDateTime = LocalDateTime.now().plusDays(30)
        val zoneId = ZoneId.systemDefault()
        val sessions = sessionRepository.findByExpiresLessThan(Timestamp(date.atZone(zoneId).toEpochSecond()))
        for (session in sessions){
            session.id?.let { sessionRepository.deleteById(it) }
        }
        return
    }

    fun getToken(id: Int): String {
        return JWT.create()
            .withAudience("session")
            .withIssuer("server")
            .withClaim("id", id)
            .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusDays(30)))
            .sign(Algorithm.HMAC256("secretauthword"))
    }
}