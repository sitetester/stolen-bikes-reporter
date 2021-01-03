package com.example.stolenbikeshandler.controller

import com.example.stolenbikeshandler.entity.BikeTheft
import com.example.stolenbikeshandler.entity.PoliceAvailability
import com.example.stolenbikeshandler.entity.PoliceOfficer
import com.example.stolenbikeshandler.helper.FileUploadHelper
import com.example.stolenbikeshandler.repository.BikeTheftsRepository
import com.example.stolenbikeshandler.repository.PoliceOfficerRepository
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RestController
@RequestMapping("/bikes_theft")
class BikesTheftController(
    private val bikeTheftsRepository: BikeTheftsRepository,
    private val policeOfficerRepository: PoliceOfficerRepository
) {

    @PostMapping("/upload-bike-image/{id}")
    fun uploadBikeImage(
        @PathVariable("id") id: Long,
        @RequestParam("image") multipartFile: MultipartFile
    ): Any {

        val savedBikeTheft = bikeTheftsRepository.findById(id)
        if (!savedBikeTheft.isPresent) {
            return ErrorMsg("No bike theft found for given id")
        }

        val bikeTheft = savedBikeTheft.get()

        val fileName: String = StringUtils.cleanPath(multipartFile.originalFilename!!)
        val uploadDir = "bike_images/" + bikeTheft.id
        FileUploadHelper().saveFile(uploadDir, fileName, multipartFile)

        bikeTheft.image = fileName
        bikeTheftsRepository.save(bikeTheft)

        return SuccessMsg()
    }

    @PostMapping("/report")
    fun reportStolenBike(@RequestBody bikeTheft: BikeTheft): Any {

        if (bikeTheft.citizenId == 0) {
            return ErrorMsg("Missing `citizenId` parameter/value")
        }

        if (bikeTheft.title == null) {
            return ErrorMsg("Missing `title` parameter/value")
        }

        val title = bikeTheft.title
        val bikeTheftWithTitle = bikeTheftsRepository.findFirstByTitle(title)
        if (bikeTheftWithTitle != null) {
            return ErrorMsg("A theft with such title already reported.")
        }

        val officer = policeOfficerRepository.findFirstByAvailability()
        bikeTheft.officer = officer

        val savedBikeTheft = bikeTheftsRepository.save(bikeTheft)
        if (officer != null) {
            officer.availability = PoliceAvailability.ASSIGNED.type
            policeOfficerRepository.save(officer)
        }

        return savedBikeTheft
    }

    // TODO: This should be visible only to authorized users
    @PostMapping("/mark-as-found")
    fun markAsFound(@RequestBody byId: ById): Any {

        val savedBikeTheft = bikeTheftsRepository.findById(byId.id)
        if (!savedBikeTheft.isPresent) {
            return ErrorMsg("No bike theft found for given id")
        }

        val bikeTheft = savedBikeTheft.get()
        bikeTheft.solved = true
        bikeTheft.updated = LocalDateTime.now()
        bikeTheft.officer = null
        bikeTheftsRepository.save(bikeTheft)

        if (bikeTheft.officer != null) {
            val officer = bikeTheft.officer as PoliceOfficer
            val policeOfficer = policeOfficerRepository.findById(officer.ID!!).get()
            policeOfficer.availability = PoliceAvailability.AVAILABLE.type
            policeOfficerRepository.save(policeOfficer)
        }

        return SuccessMsg()
    }

    @PostMapping("/assign-officer")
    fun assignOfficer(@RequestBody assignPolice: AssignPolice): Any {

        val savedBikeTheft = bikeTheftsRepository.findById(assignPolice.id)
        if (!savedBikeTheft.isPresent) {
            return ErrorMsg("No bike theft found for given ID")
        }

        if (savedBikeTheft.get().solved == true) {
            return ErrorMsg("This theft is already solved")
        }


        val policeOfficer = policeOfficerRepository.findById(assignPolice.officerId)
        if (!policeOfficer.isPresent) {
            return ErrorMsg("No officer found for given ID.")
        }

        if (policeOfficer.get().availability == PoliceAvailability.ASSIGNED.type) {
            return ErrorMsg("This officer is already assigned")
        }

        savedBikeTheft.get().officer = policeOfficer.get()
        bikeTheftsRepository.save(savedBikeTheft.get())

        return SuccessMsg()
    }
}

data class ById(val id: Long)
data class AssignPolice(val officerId: Long, val id: Long)