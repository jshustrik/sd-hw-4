package se.hse.OrderMicroservice.Entity

import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@Table(name = "station")
@NoArgsConstructor
data class Station(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    var station: String
)
