package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import org.springframework.web.bind.annotation.*

/**
 * Interface for the api from the admin
 */
@RequestMapping("api/admin")
interface AdminApi {

    @GetMapping("/reported/recipes")
    @ResponseBody
    fun getReportedPublicRecipe(@RequestParam("page") page:Int,
                                @RequestParam("readCount") readCount:Int): List<PublicRecipeDto>?

    @GetMapping("/reported/users")
    @ResponseBody
    fun getReportedUser(@RequestParam("page") page:Int,
                                @RequestParam("readCount") readCount:Int): List<UserDto>?

    @DeleteMapping("/reported/{recipeId}")
    fun deReportPublicRecipe(@RequestParam("recipeId") recipeId:Int)

    @DeleteMapping("/reported/{userId}")
    fun deReportUser(@RequestParam("userId") userId:String)
}