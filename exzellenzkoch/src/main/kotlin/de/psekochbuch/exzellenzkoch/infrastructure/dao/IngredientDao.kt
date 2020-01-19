package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientAmount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Offer database actions for ingredients
 */
interface IngredientDao :JpaRepository<IngredientAmount,Int>
{
    /**
     * Add ingredient to the database
     * @param chapter Id of the chapter
     * @param nameIng Name of the ingredient
     * @param amount Amount of the ingredient
     * @param unit Unit of the amount
     */
    @Query ("INSERT INTO ingredient_amount (chapter_Id, name_Ingredient, amount, unit) VALUES ((:chapter), (:nameIng), (:amount), (:unit));", nativeQuery = true)
    fun addIngredient(chapter:Int, nameIng:String, amount:Int, unit:String)

    /**
     * Load ingredients from a chapter
     * @param id of the chapter
     * @return List of ingredients
     */
    @Query("SELECT * FROM ingredient_amount p WHERE chapter_id = (:id)", nativeQuery = true)
    fun getIngredientsFromChapter(id:Int) : List<IngredientAmount>

    /**
     * Delete all ingredients from a chapter
     * @param id Id of the chapter
     */
    @Query("DELETE FROM ingredient_amount WHERE chapter_id = (:id)", nativeQuery = true)
    fun deleteIngredientsFromChapter(id:Int)
}