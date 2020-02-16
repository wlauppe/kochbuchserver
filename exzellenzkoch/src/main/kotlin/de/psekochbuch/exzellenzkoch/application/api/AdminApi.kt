package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import org.springframework.web.bind.annotation.*

/**
 * Interface for the api from the admin
 */
@RequestMapping("api/admin")
interface AdminApi {

    /**
     * Load the reported recipes
     * @param page The page of the database
     * @param readCount Count of reported recipes, which should load
     * @return The reported recipes
     */
    @GetMapping("/reported/recipes")
    @ResponseBody
    fun getReportedPublicRecipe(@RequestParam("page") page:Int,
                                @RequestParam("readCount") readCount:Int): List<PublicRecipeDto>?

    /**
     * Load the reported user
     * @param page The page of the database
     * @param readCount Count of reported user, which should load
     */
    @GetMapping("/reported/users")
    @ResponseBody
    fun getReportedUser(@RequestParam("page") page:Int,
                                @RequestParam("readCount") readCount:Int): List<UserDto>?

    /**
     * Set the report flag from a recipe back
     * @param recipeId Id of the recipe
     */
    @DeleteMapping("/reported/{recipeId}")
    fun deReportPublicRecipe(@RequestParam("recipeId") recipeId:Int)

    /**
     * Set the report from user flag back
     * @param userId Id of the User
     */
    @DeleteMapping("/reported/{userId}")
    fun deReportUser(@RequestParam("userId") userId:String)
}