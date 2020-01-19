package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface IngredientChapterDao : JpaRepository<IngredientChapter,Int>
{
    /**
     * Add a chapter to the database
     * @param recipeId Id of the recipe
     * @param chapterN Name of the chapter
     */
    @Query("INSERT INTO ingredient_chapter (recipe_Id, chapter_Name) VALUES((:recipeId), (:chapterN));", nativeQuery = true)
    fun addChapter(recipeId:Int, chapterN:String)

    /**
     * Load a chapters of a recipe from the database
     * @param id Id of the recipe
     * @return List of chapters of the recipe
     */
    @Query("SELECT * FROM ingredient_chapter u WHERE u.recipe_Id = (:id)", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>

    /**
     * Load the id from the last insert chapter
     */
    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
    fun getLastId(): Int

    @Query("SELECT chapter_id FROM ingredient_chapter WHERE recipe_id = (:recipeId)", nativeQuery = true)
    fun getChapterIdsFromRecipe(recipeId: Int) : List<Int>
}