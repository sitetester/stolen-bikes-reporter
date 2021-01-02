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
    var ID: Long? = null,

    var name: String? = null
)