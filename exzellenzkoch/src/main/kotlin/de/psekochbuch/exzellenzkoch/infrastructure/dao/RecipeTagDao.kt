package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.RecipeTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RecipeTagDao : JpaRepository<RecipeTag, String> {
    @Query("SELECT * FROM recipe_tag p WHERE recipe_Id = (:id)", nativeQuery = true)
    fun getRecipeTagsFromRecipe(id:Int): List<RecipeTag>
}