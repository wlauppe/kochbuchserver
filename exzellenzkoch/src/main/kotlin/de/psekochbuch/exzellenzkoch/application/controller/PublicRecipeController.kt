package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.PublicRecipeApi
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.service.PublicRecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

/**
 * Implements the PublicRecipeApi
 */
@RestController
class PublicRecipeController :PublicRecipeApi
{
    @Autowired
    private var service:PublicRecipeService? = null

    /**
     * Call the service, which load the recipe
     * @param id Id of the Recipe
     * @return The recipe with the specific id
     */
    override fun getRecipe(id: Int) : PublicRecipeDto? {
       return service?.getRecipe(id)
    }

    /**
     * Call the service, which add the recipe
     * @param publicRecipe Recipe to add
     */
    override fun addRecipe(publicRecipe: PublicRecipeDto?) :PublicRecipeDto? {
        return service?.addRecipe(publicRecipe)
    }

    /**
     * Call the service, which update the recipe with the id
     * @param publicRecipe The Recipe to update
     * @param id The id of the recipe to update
     */
    override fun updateRecipe(publicRecipe: PublicRecipeDto?, id: Int) {
        service?.updateRecipe(publicRecipe, id)
    }

    /**
     * Call the service, which delete the recipe with the id
     * @param id Id of the recipe
     */
    override fun deleteRecipe(id: Int) {
        service?.deleteRecipe(id)
    }

    /**
     * Call the service, which search recipes
     * @param title Optional parameter title to search
     * @param tags Optional parameter tags to search
     * @param ingredients Optional parameter ingredients to search
     * @param creationDate Optional parameter creationDate to search
     * @param page The page of the already loaded recipes
     * @param readCount Count of the to loaded recipes
     * @return List of the recipes
     */
    override fun search(title: Optional<String>, tags: Optional<List<String>>, ingredients: Optional<List<String>>, creationDate: Optional<Date>, page: Int, readCount: Int): List<PublicRecipeDto>? {

        return service?.search(title, tags, ingredients, creationDate,page,readCount)
    }

    /**
     * Call the service which report the recipe
     * @param id Id of the recipe
     */
    override fun reportRecipe(id: Int) {
        service?.reportRecipe(id)
    }


}