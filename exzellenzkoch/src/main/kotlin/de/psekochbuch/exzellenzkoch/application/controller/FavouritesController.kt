package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.FavouritesApi
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.service.FavouritesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

/**
 * Implementation from the FavouritesApi
 */
@RestController
class FavouritesController : FavouritesApi {

    @Autowired
    private val service : FavouritesService? = null

    override fun addFavRecipe(userId: String, recipeId: Int) {
        service?.addFavRecipe(userId,recipeId)
    }

    override fun getFavRecipes(userId: String, page: Int, readCount: Int): List<PublicRecipeDto>? {
        return service?.getFavRecipes(userId,page,readCount)
    }

    override fun delFavRecipe(userId: String, recipeId: Int) {
        service?.delFavRecipe(userId,recipeId)
    }
}