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


@Repository
interface PublicRecipeDao : JpaRepository<PublicRecipe?, Int?>, PublicRecipeRepository
{
    @Query("SELECT new de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter(t.chapter_Id, null, t.chapter_Name) FROM ingredient_chapter u WHERE u.recipe_Id = 1", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>

    @Query("SELECT p FROM ingredient_amount p WHERE chapter_id = (:id)", nativeQuery = true)
    fun getIngredientsFromChapter(id:Int) : List<IngredientAmount>

    @Query("SELECT p FROM recipe_tag p WHERE recipe_Id = (:id)", nativeQuery = true)
    fun getRecipeTagsFromRecipe(id:Int): List<RecipeTag>

    @Query("INSERT INTO public_recipe (title, ingredients_Text, preparation_Description, picture, cooking_Time, preparation_Time, user_Id, creation_Date, portions, mark_As_Evil) VALUES ((:title), (:ingr_Text), (:prep_Desc), (:pic), (:cook_T), (:prep_T), (:user), (:cre_D), (:port), 0)", nativeQuery = true)
    fun addRecipe(title:String, ingr_Text:String, prep_Desc:String, pic:ByteArray, cook_T:Int, prep_T:Int, user:String, cre_D: Date, port:Int)


    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
    fun getLastId(): Int

    @Query("UPDATE public_recipe SET mark_as_Evil = 1 WHERE recipe_id = (:id)", nativeQuery = true)
    fun reportRecipe(id: Int)
}
