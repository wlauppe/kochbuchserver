package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.RecipeTagDto
import de.psekochbuch.exzellenzkoch.domain.model.IngredientAmount
import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.RecipeTag
import de.psekochbuch.exzellenzkoch.infrastructure.dao.IngredientChapterDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.IngredientDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.RecipeTagDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao

/**
 * This class converts DTOs to modelobjects and modelobjects to DTOs
 */
object RecipeConverter
{
    /**
     * Converts public recipes to DTO
     * @param recipe The recipe, which should convert
     * @param ingredientChapterDao Database Access Object of ingredientchapters
     * @param recipeTagDao Database Access Object of recipetags
     * @param ingredientAmountDao Database Access Object of ingredients
     * @return The converted PublicRecipeDto
     */
    fun convertRecipeToDto(recipe: PublicRecipe?,  ingredientChapterDao:IngredientChapterDao?, recipeTagDao: RecipeTagDao?, ingredientAmountDao:IngredientDao?) : PublicRecipeDto
    {
        //var bao:ByteArrayOutputStream = ByteArrayOutputStream()

        //ImageIO.write(recipe?.picture, "jpg", bao)
        /* if(recipe?.picture == null)
         {
             recipe?.picture = "0".toByteArray(Charsets.UTF_8)
         }*/
        val recipeChapter = ingredientChapterDao?.getChapterFromRecipe(recipe!!.recipeId)
        val recipeChapterDto = convertRecipeChapterToDto(recipeChapter, ingredientAmountDao)

        val recipeTagDto = convertRecipeTagsToDto(recipeTagDao?.getRecipeTagsFromRecipe(recipe!!.recipeId))

        return PublicRecipeDto(recipe!!.recipeId, recipe.title, recipe.ingredientsText, recipe.preparationDescription, recipe.picture, recipe.cookingTime, recipe.preparationTime, recipe.user?.userId, recipe.creationDate, recipe.portions, 0, recipeChapterDto, recipeTagDto)
    }

    /**
     * Converts recipe tags to DTOs
     * @param recipeTags Tags of the recipe
     * @return The converted recipes tags
     */
    private fun convertRecipeTagsToDto(recipeTags: List<RecipeTag>?): List<RecipeTagDto> {
        val convertedRecipeTags:MutableList<RecipeTagDto> = ArrayList<RecipeTagDto>()
        recipeTags?.forEach{
            convertedRecipeTags.add(RecipeTagDto(it.tagId))
        }
        return convertedRecipeTags
    }

    /**
     * Converts ingredientchapters to DTOs
     * @param recipeChapters chapters, which should convert
     * @param ingredientAmountDao Database Access Object of ingredients
     * @return List of converted ingredientchapters
     */
    private fun convertRecipeChapterToDto(recipeChapters: List<IngredientChapter>?, ingredientAmountDao: IngredientDao?) : List<IngredientChapterDto>{
        val convertedChapters:MutableList<IngredientChapterDto> = ArrayList<IngredientChapterDto>()
        recipeChapters?.forEach {
            val convertedIngredients = convertChapterIngredientsToDto(ingredientAmountDao?.getIngredientsFromChapter(it.chapterId))
            convertedChapters.add(IngredientChapterDto(it.chapterId,it.chapterName, convertedIngredients))
        }
        return convertedChapters
    }

    /**
     * Converts ingredients from chapters to DTOs
     * @param ingredientsFromChapter List of ingredients
     * @return List of converted ingredients
     */
    private fun convertChapterIngredientsToDto(ingredientsFromChapter: List<IngredientAmount>?): List<IngredientDto> {
        val convertedIngredients:MutableList<IngredientDto> = ArrayList()
        ingredientsFromChapter?.forEach {
            convertedIngredients.add(IngredientDto(it.chapter!!.chapterId, it.nameIngredient, it.amount,it.unit))
        }
        return convertedIngredients
    }

    /**
     * Converts public recipes to DTOs
     * @param
     */
    fun convertDtoToRecipe(recipe:PublicRecipeDto, userDao: UserDao) : PublicRecipe?
    {
        val userId:String
        if(recipe.userId == null)
        {
            return null
        }
        else
        {
            userId = recipe.userId
        }
        val user = userDao.findById(userId).get()
        return PublicRecipe(recipe.id,recipe.title,recipe.ingredientsText,recipe.preparationDescription, recipe.picture,recipe.cookingTime,recipe.preparationTime, user, recipe.creationDate, false, recipe.portions, null)
    }

    fun convertDtoToIngredient(saveChapter: IngredientChapter?, ingredient: List<IngredientDto>?) :List<IngredientAmount> {
        val convertedIngredient: MutableList<IngredientAmount> = ArrayList()
        ingredient?.forEach {
            convertedIngredient.add(IngredientAmount(saveChapter,it.nameIngredient,it.amount,it.unit))
        }
        return convertedIngredient
    }


}