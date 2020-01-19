package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.RecipeTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Offer database actions for public recipe tags
 */
interface RecipeTagDao : JpaRepository<RecipeTag, String> {

    /**
     * Add public recipe tag to database
     * @param tagId Id/Name of the tag
     * @param recipeId Id of the recipe
     */
    @Query("INSERT INTO recipe_tag (tag_Id, recipe_Id) VALUES ((:tagId), (:recipeId));", nativeQuery = true)
    fun addRecipeTag(tagId:String, recipeId:Int)

    /**
     * load all public recipe tags of a recipe
     * @param id Id of the recipe
     * @return List of recipe tags
     */
    @Query("SELECT * FROM recipe_tag p WHERE recipe_Id = (:id)", nativeQuery = true)
    fun getRecipeTagsFromRecipe(id:Int): List<RecipeTag>

}