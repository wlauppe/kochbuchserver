package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.CustomTokenDto
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
     * @param userId The id of user to create
     */
    @PostMapping("/{userId}")
    fun createUser(@PathVariable userId:String) :CustomTokenDto?

    /**
     * POST-Request to create a new user
     * The URL ends with /api/users
     * @param userId The id of user to create
     */
    @PostMapping("/")
    fun createUser() :CustomTokenDto?

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

    /**
     * GET-Request to get a user with specific id
     * The URL ends with /api/users/check/{userId}
     * @param userId The id of the user
     */
    @GetMapping("/check/{userId}")
    fun checkUser(@PathVariable userId: String) : UserDto?

    /**
     * POST-Request to report a user
     * The URL ends with /api/users/report/{id}
     * @param id Id of the user
     */
    @PostMapping("/report/{id}")
    fun reportRecipe(@PathVariable(value = "id") id:String)
}