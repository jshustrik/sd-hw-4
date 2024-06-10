package se.hse.OrderMicroservice.Entity

import jakarta.persistence.*
import lombok.NoArgsConstructor
import java.sql.Timestamp

@Entity
@Table(name = "orders")
@NoArgsConstructor
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    var userId: Int,

    @Column(nullable = false)
    var fromStationId: Int,

    @Column(nullable = false)
    var toStationId: Int,

    @Column(nullable = false)
    var status: Int,

    var created: Timestamp = Timestamp(System.currentTimeMillis()),
)
