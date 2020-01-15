package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.*
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Class for management of public recipes
 */
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

        publicRecipeDao?.addRecipe(publicRecipe.title,publicRecipe.ingredientsText,publicRecipe.preparationDescription,publicRecipe.picture, publicRecipe.cookingTime, publicRecipe.preparationTime, publicRecipe.userId, publicRecipe.creationDate, publicRecipe.portions)
        publicRecipeDao?.flush()

        val recipeId = publicRecipeDao?.getLastId() ?: return

        createChapterWithIngredients(publicRecipe, recipeId)

        publicRecipe.recipeTag?.forEach{
            recipeTagDao?.addRecipeTag(it.name ,recipeId)
        }
    }

    fun deleteRecipe(id: Int) {
        val publicRecipe = publicRecipeDao?.findById(id)?.get()
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(auth.principal == publicRecipe?.user?.userId)
        {
            deleteChapterWithIngredients(id)

            recipeTagDao?.getRecipeTagsFromRecipe(id)?.forEach {
                recipeTagDao?.delete(it)
            }
            publicRecipeDao?.deleteById(id)
        }
    }

    fun updateRecipe(publicRecipe: PublicRecipeDto?, id: Int) {
        publicRecipeDao?.findById(id)?.map {
            it?.title = publicRecipe!!.title
            it?.ingredientsText = publicRecipe.ingredientsText
            it?.preparationDescription = publicRecipe.preparationDescription
            it?.picture = publicRecipe.picture
            it?.cookingTime = publicRecipe.cookingTime
            it?.preparationTime = publicRecipe.preparationTime
            it?.portions = publicRecipe.portions
            if (it != null) publicRecipeDao?.save(it)
        }

        deleteChapterWithIngredients(publicRecipe!!.id)
        createChapterWithIngredients(publicRecipe, id)


    }

    fun search(title:String, tags:List<String>, ingredients:List<String>,
               creationDate: Date, alreadyLoad:Int, readCount:Int)
    {
        //publicRecipeDao.search()
    }

    private fun createChapterWithIngredients(publicRecipe: PublicRecipeDto, recipeId: Int)
    {
        publicRecipe.ingredientsChapter?.forEach { chapter ->

            ingredientChapterDao?.addChapter(recipeId,chapter.name)
            ingredientChapterDao?.flush()
            val chapterId = ingredientChapterDao?.getLastId() ?: return
            chapter.ingredient?.forEach{
                ingredientAmountDao?.addIngredient(chapterId, it.nameIngredient,it.amount, it.unit)
                ingredientAmountDao?.flush()
            }
        }
    }

    private fun deleteChapterWithIngredients(recipeId:Int)
    {
        ingredientChapterDao?.getChapterIdsFromRecipe(recipeId)?.forEach{
            ingredientAmountDao?.deleteIngredientsFromChapter(it)
            ingredientChapterDao?.deleteById(it)
        }

    }
}