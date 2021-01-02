package com.example.stolenbikeshandler.repository

import com.example.stolenbikeshandler.entity.PoliceOfficer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PoliceOfficerRepository : JpaRepository<PoliceOfficer, Long> {

    fun findByName(name: String): List<PoliceOfficer>?

}