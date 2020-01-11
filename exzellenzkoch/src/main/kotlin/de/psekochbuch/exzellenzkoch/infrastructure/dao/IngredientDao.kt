package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientAmount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IngredientDao :JpaRepository<IngredientAmount,Int>
{
    @Query("SELECT * FROM ingredient_amount p WHERE chapter_id = (:id)", nativeQuery = true)
    fun getIngredientsFromChapter(id:Int) : List<IngredientAmount>
}