package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
//@Component
@Transactional
class PublicRecipeService
{
    @Autowired var publicRecipeDao:PublicRecipeDao? = null
    @Autowired var userDao:UserDao? = null
    @Autowired var ingredientChapterDao: IngredientChapterDao? = null
    @Autowired var recipeTagDao: RecipeTagDao? = null
    @Autowired var ingredientAmountDao: IngredientDao? = null



    fun getRecipe(id:Int) : PublicRecipeDto
    {
        val recipe = publicRecipeDao?.findById(id)
        return RecipeConverter.convertRecipeToDto(recipe?.get(), ingredientChapterDao, recipeTagDao, ingredientAmountDao)

    }

    fun addRecipe(publicRecipe: PublicRecipeDto?) {
        if(publicRecipe?.userId == null) return
        val user = userDao?.findById(publicRecipe.userId)?.get()

        val newRecipe = PublicRecipe(0, publicRecipe.title , publicRecipe.ingredientsText, publicRecipe.preparationDescription,publicRecipe.picture, publicRecipe.cookingTime, publicRecipe.preparationTime, user, publicRecipe.creationDate,false, publicRecipe.portions, null)

        //publicRecipeJpaDao?.save(newRecipe)
        publicRecipeDao?.addRecipe(newRecipe.title,newRecipe.ingredientsText,newRecipe.preparationDescription,newRecipe.picture, newRecipe.cookingTime, newRecipe.preparationTime, publicRecipe.userId, newRecipe.creationDate, newRecipe.portions)
        publicRecipeDao?.flush()

        val i = publicRecipeDao?.getLastId() ?: return
        newRecipe.recipeId = i
        val recipe = publicRecipeDao?.findById(newRecipe.recipeId)?.get()

        publicRecipe.ingredientsChapter?.forEach {
            val convertedChapter = IngredientChapter(it.id, recipe,it.name )
            ingredientChapterDao?.addChapter(i,it.name)
            ingredientChapterDao?.flush()
            val chapterId = ingredientChapterDao?.getLastId() ?: return
            val saveChapter = ingredientChapterDao?.findById(chapterId)?.get()
            RecipeConverter.convertDtoToIngredient(saveChapter, it.ingredient).forEach{
                ingredientAmountDao?.addIngredient(chapterId, it.nameIngredient,it.amount, it.unit)
                ingredientAmountDao?.flush()
            }

        }
    }




}