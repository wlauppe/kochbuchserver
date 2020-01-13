package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IngredientChapterDao : JpaRepository<IngredientChapter,Int>
{
    @Query("INSERT INTO ingredient_chapter (recipe_Id, chapter_Name) VALUES((:recipeId), (:chapterN));", nativeQuery = true)
    fun addChapter(recipeId:Int, chapterN:String)

    @Query("SELECT * FROM ingredient_chapter u WHERE u.recipe_Id = (:id)", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>

    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
    fun getLastId(): Int
}