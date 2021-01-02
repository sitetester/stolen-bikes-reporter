package com.example.stolenbikeshandler.controller

import com.example.stolenbikeshandler.entity.PoliceOfficer
import com.example.stolenbikeshandler.repository.PoliceOfficerRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/police_officers")
class PoliceOfficerController(private val policeOfficerRepository: PoliceOfficerRepository) {

    @PostMapping("/add")
    fun add(@RequestBody policeOfficer: PoliceOfficer): Any {

        val officers = policeOfficer.name?.let { policeOfficerRepository.findByName(it) }
        if (officers?.isNotEmpty() == true) {
            return ErrorMsg("Officer with such name already exists")
        }

        return policeOfficerRepository.save(PoliceOfficer(name = policeOfficer.name))
    }


    @PostMapping("/edit")
    fun edit(@RequestBody policeOfficer: PoliceOfficer): Any {

        val id = policeOfficer.ID ?: return ErrorMsg("`ID` parameter is missing")

        val dbPoliceOfficer = policeOfficerRepository.findById(id)
        if (!dbPoliceOfficer.isPresent) {
            return ErrorMsg("No officer found for given ID: $id")
        }

        val officers = policeOfficer.name?.let { policeOfficerRepository.findByName(it) }
        if (officers?.isNotEmpty() == true) {
            if (officers.any { officer -> officer.ID != id }) {
                return ErrorMsg("Officer with such name already exists")
            }
        }

        policeOfficerRepository.save(
            PoliceOfficer(
                ID = dbPoliceOfficer.get().ID,
                name = policeOfficer.name
            )
        )

        return SuccessMsg()
    }

    @DeleteMapping("/delete")
    fun delete(@RequestBody policeOfficer: PoliceOfficer): Any? {

        val id = policeOfficer.ID ?: return ErrorMsg("`ID` parameter is missing")

        val dbPoliceOfficer = policeOfficerRepository.findById(id)
        if (!dbPoliceOfficer.isPresent) {
            return ErrorMsg("No officer found for given ID: $id")
        }


        dbPoliceOfficer.get().ID?.let { policeOfficerRepository.deleteById(it) }
        return SuccessMsg()
    }

}

data class SuccessMsg(val msg: String = "Success")
data class ErrorMsg(val msg: String, val status: String = "Error")