package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientAmount
import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.RecipeTag
import de.psekochbuch.exzellenzkoch.infrastructure.dao.custom.PublicRecipeRepository
import de.psekochbuch.exzellenzkoch.infrastructure.dao.custom.PublicRecipeRepositoryImpl
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.criteria.Root

/**
 * Offer database actions for public recipes
 */
@Repository
interface PublicRecipeDao : JpaRepository<PublicRecipe?, Int?>, PublicRecipeRepository
{
    /**
     * Add recipe to the database
     * @param title Title of the recipe
     * @param ingr_Text All ingredients as a textline
     * @param prep_Desc Description how to create a meal
     * @param pic Picture of the meal
     * @param cook_T Time to cook a meal
     * @param prep_T Time to preparate a meal
     * @param user The user who create the recipe
     * @param cre_D Date when the recipe was created
     * @param port Count of portions for people
     */
    @Query("INSERT INTO public_recipe (title, ingredients_Text, preparation_Description, picture, cooking_Time, preparation_Time, user_Id, creation_Date, portions, mark_As_Evil) VALUES ((:title), (:ingr_Text), (:prep_Desc), (:pic), (:cook_T), (:prep_T), (:user), (:cre_D), (:port), 0)", nativeQuery = true)
    fun addRecipe(title:String, ingr_Text:String, prep_Desc:String, pic:String, cook_T:Int, prep_T:Int, user:String, cre_D: Date, port:Int)

    /**
     * Load id from last insert recipe
     * @return The id of the last insert recipe
     */
    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
    fun getLastId(): Int

    /**
     * Set the report value of a recipe
     * @param id Id of the recipe
     */
    @Query("UPDATE public_recipe SET mark_as_Evil = 1 WHERE recipe_id = (:id)", nativeQuery = true)
    fun reportRecipe(id: Int)

    @Query( "UPDATE public_recipe SET mark_as_Evil = 0 WHERE recipe_id = (:id)", nativeQuery = true)
    fun deReportRecipe(id:Int)
}
