package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PublicRecipeDao : JpaRepository<PublicRecipe?, Int?>
{
    @Query("SELECT p FROM ingredient_chapter p WHERE recipe_Id = (:id)", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>

    //@Query("SELECT p FROM publicRecipe p where recipe_id =(:id)", nativeQuery = true)
    //fun getRecipe(@Param("id") id:Int) :List<PublicRecipe>
}