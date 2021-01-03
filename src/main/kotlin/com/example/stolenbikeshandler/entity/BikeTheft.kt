package com.example.stolenbikeshandler.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "bike_thefts")
data class BikeTheft(

    @Id
    @GeneratedValue
    var id: Long,

    var title: String? = null,

    var brand: String? = null,

    // city where theft occurred
    var city: String? = null,

    var description: String? = null,

    // date and time when theft was reported
    var reported: LocalDateTime = LocalDateTime.now(),

    // date and time when case was last updated
    var updated: LocalDateTime = LocalDateTime.now(),

    // true when case has been solved
    var solved: Boolean? = null,

    var citizenId: Int,

    // image of bike
    var image: String? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    // officer in charge of the case
    var officer: PoliceOfficer? = null

)