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

object RecipeConverter
{


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

    private fun convertRecipeTagsToDto(recipeTags: List<RecipeTag>?): List<RecipeTagDto> {
        val convertedRecipeTags:MutableList<RecipeTagDto> = ArrayList<RecipeTagDto>()
        recipeTags?.forEach{
            convertedRecipeTags.add(RecipeTagDto(it.tagId))
        }
        return convertedRecipeTags
    }

    private fun convertRecipeChapterToDto(recipeChapters: List<IngredientChapter>?, ingredientAmountDao: IngredientDao?) : List<IngredientChapterDto>{
        val convertedChapters:MutableList<IngredientChapterDto> = ArrayList<IngredientChapterDto>()
        recipeChapters?.forEach {
            val convertedIngredients = convertChapterIngredientsToDto(ingredientAmountDao?.getIngredientsFromChapter(it.chapterId))
            convertedChapters.add(IngredientChapterDto(it.chapterId,it.chapterName, convertedIngredients))
        }
        return convertedChapters
    }

    private fun convertChapterIngredientsToDto(ingredientsFromChapter: List<IngredientAmount>?): List<IngredientDto> {
        val convertedIngredients:MutableList<IngredientDto> = ArrayList<IngredientDto>()
        ingredientsFromChapter?.forEach {
            convertedIngredients.add(IngredientDto(it.chapter!!.chapterId, it.nameIngredient, it.amount,it.unit))
        }
        return convertedIngredients
    }

    fun convertDtoToRecipe(recipe:PublicRecipeDto, userDao: UserDao) : PublicRecipe?
    {
        val userId:String;
        if(recipe.userId == null)
        {
            return null;
        }
        else
        {
            userId = recipe.userId
        }
        val user = userDao.findById(userId).get()
        return PublicRecipe(recipe.id,recipe.title,recipe.ingredientsText,recipe.preparationDescription, recipe.picture,recipe.cookingTime,recipe.preparationTime, user, recipe.creationDate, false, recipe.portions, null)
    }

    fun convertDtoToIngredient(saveChapter: IngredientChapter?, ingredient: List<IngredientDto>?) :List<IngredientAmount> {
        val convertedIngredient: MutableList<IngredientAmount> = ArrayList<IngredientAmount>()
        ingredient?.forEach {
            convertedIngredient.add(IngredientAmount(saveChapter,it.nameIngredient,it.amount,it.unit))
        }
        return convertedIngredient
    }


}