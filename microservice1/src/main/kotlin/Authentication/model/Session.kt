package Authentication.model

import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.sql.Timestamp

@Entity
@Table(name = "sessions")
@NoArgsConstructor
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    var userId: Int,

    @Column(nullable = false)
    var token: String,

    @Column(nullable = false)
    var expires: Timestamp,
)

