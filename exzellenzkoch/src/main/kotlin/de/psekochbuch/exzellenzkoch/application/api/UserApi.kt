package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/api/users")
interface UserApi {

    @PostMapping("")
    fun createUser(user:UserDto)

    @PutMapping("/{userId}")
    fun updateUser(user:UserDto)

    @DeleteMapping("/{userId}")
    fun deleteUser(id:String)
}