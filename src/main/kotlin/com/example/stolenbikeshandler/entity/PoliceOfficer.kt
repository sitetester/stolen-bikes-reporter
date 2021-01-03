package com.example.stolenbikeshandler.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "police_officers")
data class PoliceOfficer(

    @Id
    @GeneratedValue
    // it could be null, in case of passing of adding a new officer without ID
    var ID: Long? = null,

    var name: String? = null,

    // either available|assigned
    var availability: String? = PoliceAvailability.AVAILABLE.type,
)

enum class PoliceAvailability(val type: String) {
    ASSIGNED("assigned"),
    AVAILABLE("available")
}