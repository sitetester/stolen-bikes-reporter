package com.example.stolenbikeshandler.repository

import com.example.stolenbikeshandler.entity.BikeTheft
import org.springframework.data.jpa.repository.JpaRepository

interface BikeTheftsRepository : JpaRepository<BikeTheft, Long>