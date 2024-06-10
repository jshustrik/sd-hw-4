package Authentication.controllers

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import Authentication.dto.IdDTO
import Authentication.dto.LoginDTO
import Authentication.dto.SessionDTO
import Authentication.dto.UserDTO
import Authentication.service.AuthService

@RestController
@RequestMapping("/user")
class AuthController(val authService: AuthService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserDTO): ResponseEntity<String> {
        return try {
            val response = authService.registerUser(user)
            ResponseEntity.ok(response)
        } catch (e:Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDTO): ResponseEntity<String> {
        return try {
            val response = authService.login(loginDto)
            ResponseEntity.ok(response)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.BAD_REQUEST)
        }

    }

    @DeleteMapping("/logout")
    fun logout(@RequestBody sessionDto: SessionDTO): ResponseEntity<String> {
        return try {
            val response = authService.logout(sessionDto)
            ResponseEntity.ok(response)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/userinfo")
    fun getUserInfo(@RequestBody sessionDto: SessionDTO): ResponseEntity<String> {
        return try {
            val resp = authService.getUserInfo(sessionDto)
            ResponseEntity.ok(resp)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/check")
    fun checkUser(@RequestBody userId: IdDTO): ResponseEntity<String> {
        return try {
            val resp = authService.checkUser(userId.id)
            ResponseEntity.ok(resp)
        }
        catch (e: Exception){
            ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
        }
    }
}