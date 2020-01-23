package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Interface for the api from the users
 */
@RequestMapping("/api/users")
interface UserApi {

    /**
     * POST-Request to create a new user
     * The URL ends with /api/users
     * @param user The user to create
     */
    @PostMapping("")
    fun createUser(@Valid @RequestBody user:UserDto)

    /**
     * PUT-Request to update an user
     * The URL ends with /api/users/{userId}
     * @param user The user to update
     * @param userId The id of the user
     */
    @PutMapping("/{userId}")
    fun updateUser(@Valid @RequestBody user:UserDto, @PathVariable userId: String)

    /**
     * DELETE-Request to delete an user
     * The URL ends with /api/users/{userId}
     * @param userId The id of the user who should delete
     */
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId:String)

    /**
     * GET-Request to get a user with specific id
     * The URL ends with /api/users/{userId}
     * @param userId The id of the user
     */
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String) : UserDto?
}