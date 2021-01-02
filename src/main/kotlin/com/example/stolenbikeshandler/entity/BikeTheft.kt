package com.example.stolenbikeshandler.entity

import java.sql.Blob
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "bike_thefts")
class BikeTheft {

    @Id
    @GeneratedValue
    var id: Long? = null

    var title: String? = null

    var brand: String? = null

    // city where theft occurred
    var city: String? = null

    var description: String? = null

    // date and time when theft was reported
    var reported: String? = null

    // date and time when case was last updated
    var updated: String? = null

    // true when case has been solved
    var solved: Boolean? = null

    // officer in charge of the case
    var officer: Int = 0

    // image of bike
    var image: Blob? = null

}