package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

/**
 * Interface for the api from the public recipes
 */
@RequestMapping("/api/recipes")
interface PublicRecipeApi {

    /**
     * GET-Request to get a recipe with a specific id.
     * The URl ends with /api/recipes/{id}
     * @param id Id of the recipe
     * @return The recipe with the specific id
     */
    @GetMapping("/{id}")
    fun getRecipe(@PathVariable(value = "id") id:Int) : PublicRecipeDto?

    /**
     * POST-Request to add a new recipe.
     * The URL ends with /api/recipes
     * @param publicRecipe Recipe to add
     */
    @PostMapping ("")
    fun addRecipe(@Valid @RequestBody publicRecipe:PublicRecipeDto?)

    /**
     * PUT-Request to update a recipe
     * The URL ends with /api/recipes/{id}
     * @param publicRecipe Recipe to update
     * @param id Id of the recipe to update
     */
    @PutMapping ("/{id}")
    fun updateRecipe(@Valid @RequestBody publicRecipe: PublicRecipeDto?, @PathVariable id:Int)

    /**
     * DELETE-Request to delete a recipe
     * The URL ends with /api/recipes/{id}
     * @param id Id of the recipe
     */
    @DeleteMapping ("/{id}")
    fun deleteRecipe(@PathVariable(value = "id") id:Int)

    /**
     * GET-Request to search a recipe with ciriterias
     * The URL ends with /api/recipes
     * @param title Optional parameter title to search
     * @param tags Optional parameter tags to search
     * @param ingredients Optional parameter ingredients to search
     * @param creationDate Optional parameter creationDate to search
     * @param page The page of the already loaded recipes
     * @param readCount Count of the to loaded recipes
     * @return List of the recipes
     */
    @GetMapping("")
    fun search(@RequestParam("title") title:Optional<String>,
               @RequestParam("tags") tags:Optional<List<String>>,
               @RequestParam("ingredients") ingredients:Optional<List<String>>,
               @RequestParam("creationDate") creationDate:Optional<Date>,
               @RequestParam("page") page:Int,
               @RequestParam("readCount") readCount:Int):List<PublicRecipeDto>?

    /**
     * POST-Request to report a recipe
     * The URL ends with /api/recipes/report/{id}
     * @param id Id of the recipe
     */
    @PostMapping("/report/{id}")
    fun reportRecipe(@PathVariable(value = "id") id:Int)

}