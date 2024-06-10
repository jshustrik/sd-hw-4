package Authentication.model

import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.sql.Timestamp

@Entity
@Table(name = "users")
@NoArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    var nickname: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    var created: Timestamp = Timestamp(System.currentTimeMillis())
)
