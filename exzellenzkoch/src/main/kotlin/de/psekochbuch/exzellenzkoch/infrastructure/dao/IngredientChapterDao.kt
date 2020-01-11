package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IngredientChapterDao : JpaRepository<IngredientChapter,Int>
{
    @Query("SELECT * FROM ingredient_chapter u WHERE u.recipe_Id = (:id)", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>
}