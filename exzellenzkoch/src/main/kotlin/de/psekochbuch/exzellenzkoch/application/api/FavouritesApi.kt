package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import org.springframework.web.bind.annotation.*

/**
 * Interface for the api to handle favourites
 */
@RequestMapping("/api/users")
interface FavouritesApi
{
    /**
     * POST-Request to add a favourite to an user.
     * The URL ends with /api/users/userId/favourites/recipeId
     * @param userId The id of the user
     * @param recipeId The id of the recipe
     */
    @PutMapping("/{userId}/favourites/{recipeId}")
    fun addFavRecipe(@PathVariable userId:String, @PathVariable recipeId:Int)

    /**
     * GET-Request to get the favourites of an user.
     * The URl ends with /api/users/userId/favourites
     * @param userId Id of the user
     * @param page The page of the already loaded recipes
     * @param readCount Count of the to loaded recipes
     * @return The favourite recipes from an user
     */
    @GetMapping("/{userId}/favourites")
    fun getFavRecipes(@PathVariable("userId") userId: String,
                      @RequestParam("page", required = false) page:Int,
                      @RequestParam("readCount", required = false) readCount:Int) :List<PublicRecipeDto>?

    /**
     * DELETE-Request to delete a favourite of an user
     * The URL ends with /api/users/userId/favourites/recipeId
     * @param userId The id of the user
     * @param recipeId The id of the recipe
     */
    @DeleteMapping("/{userId}/favourites/{recipeId}")
    fun delFavRecipe(@PathVariable userId: String, @PathVariable recipeId:Int)
}