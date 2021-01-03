package com.example.stolenbikeshandler.repository

import com.example.stolenbikeshandler.entity.PoliceAvailability
import com.example.stolenbikeshandler.entity.PoliceOfficer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PoliceOfficerRepository : JpaRepository<PoliceOfficer, Long> {

    fun findByName(name: String): List<PoliceOfficer>?

    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.limit-query-result
    fun findFirstByAvailability(availability: String = PoliceAvailability.AVAILABLE.type): PoliceOfficer?

}