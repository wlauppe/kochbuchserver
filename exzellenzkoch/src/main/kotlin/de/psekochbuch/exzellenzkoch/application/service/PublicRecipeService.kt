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
import kotlin.collections.ArrayList

/**
 * Class for management of public recipes
 */
@Service
@Transactional
class PublicRecipeService
{
    @Autowired private var publicRecipeDao:PublicRecipeDao? = null
    @Autowired private var ingredientChapterDao: IngredientChapterDao? = null
    @Autowired private var recipeTagDao: RecipeTagDao? = null
    @Autowired private var ingredientAmountDao: IngredientDao? = null

    /**
     * Load, convert and return the recipe with the id
     * @param id Id of the recipe
     * @return The recipe with the specific id
     */
    fun getRecipe(id:Int) : PublicRecipeDto
    {
        val recipe = publicRecipeDao?.findById(id)
        return RecipeConverter.convertRecipeToDto(recipe?.get(), ingredientChapterDao, recipeTagDao, ingredientAmountDao)
    }

    /**
     * Convert the recipe to PublicRecipe and add it to the database
     * Add the ingredients and tags to the database
     *@param publicRecipe The recipe that should add to the database
     */
    fun addRecipe(publicRecipe: PublicRecipeDto?) {
        if(publicRecipe?.userId == null) return

        publicRecipeDao?.addRecipe(publicRecipe.title,publicRecipe.ingredientsText,publicRecipe.preparationDescription, "", publicRecipe.cookingTime, publicRecipe.preparationTime, publicRecipe.userId, publicRecipe.creationDate, publicRecipe.portions)
        publicRecipeDao?.flush()

        val recipeId = publicRecipeDao?.getLastId() ?: return

        createChapterWithIngredients(publicRecipe, recipeId)
        deleteRecipeTagFromRecipe(recipeId)

        publicRecipe.recipeTag?.forEach{
            recipeTagDao?.addRecipeTag(it.name ,recipeId)
        }
    }

    /**
     * Delete the recipe with the specific id
     * @param id The id from the recipe
     */
    fun deleteRecipe(id: Int) {
        val publicRecipe = publicRecipeDao?.findById(id)?.get()
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(auth.principal == publicRecipe?.user?.userId)
        {
            deleteChapterWithIngredients(id)
            deleteRecipeTagFromRecipe(id)
            publicRecipeDao?.deleteById(id)
        }
    }

    /**
     * Update the recipe with the specific id and the new values
     * Delete all Ingredients and Tags and add the new one
     * @param publicRecipe The recipe to update
     * @param id Id of the recipe
     */
    fun updateRecipe(publicRecipe: PublicRecipeDto?, id: Int) {

        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(auth.principal == publicRecipe?.userId) {
            publicRecipeDao?.findById(id)?.map {
                it?.title = publicRecipe.title
                it?.ingredientsText = publicRecipe.ingredientsText
                it?.preparationDescription = publicRecipe.preparationDescription
                it?.picture = publicRecipe.picture
                it?.cookingTime = publicRecipe.cookingTime
                it?.preparationTime = publicRecipe.preparationTime
                it?.portions = publicRecipe.portions
                if (it != null) publicRecipeDao?.save(it)
            }

            deleteChapterWithIngredients(publicRecipe.id)
            createChapterWithIngredients(publicRecipe, id)
        }
    }

    /**
     * Search in the database after recipes with specific title, tags, ingredients or date
     * @param title The title which should search
     * @param tags The tags from the recipes
     * @param ingredients The ingredients from the recipes
     * @param page The page of the already loaded recipes
     * @param readCount Count of the to loaded recipes
     * @return the loaded recipes
     */
    fun search(title:Optional<String>, tags:Optional<List<String>>, ingredients:Optional<List<String>>,
               creationDate:Optional<Date>, page:Int, readCount:Int) : List<PublicRecipeDto>
    {
        val t:String? = if(!title.isEmpty) '%' + title.get() + '%' else null
        val tag:List<String>? = if(!tags.isEmpty) tags.get() else null
        val ingredient:List<String>? = if(!ingredients.isEmpty) ingredients.get() else null
        val date:Date? = if(!creationDate.isEmpty) creationDate.get() else null

        val recipes:MutableList<PublicRecipeDto> = ArrayList()
        publicRecipeDao?.search(t,tag,ingredient,date,page,readCount)?.forEach {
            if(it is PublicRecipe)
            {
                recipes.add(RecipeConverter.convertRecipeToDto(it,ingredientChapterDao,recipeTagDao,ingredientAmountDao))
            }
        }
        return recipes
    }

    /**
     * Report a recipe
     * @param id The id of the to reported recipe
     */
    fun reportRecipe(id: Int) {
        publicRecipeDao?.reportRecipe(id)
    }

    /**
     * Create Chapters with there ingredients in the database
     * @param publicRecipe the recipe form the ingredients and chapters
     * @param recipeId the id from the recipe from the database
     */
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

    /**
     * Delete chapters and ingredients from a recipe
     * @param recipeId The id from the recipe
     */
    private fun deleteChapterWithIngredients(recipeId:Int)
    {
        ingredientChapterDao?.getChapterIdsFromRecipe(recipeId)?.forEach{
            ingredientAmountDao?.deleteIngredientsFromChapter(it)
            ingredientChapterDao?.deleteById(it)
        }

    }

    /**
     * Delete tags from a recipe
     * @param recipeId The id from the recipe
     */
    private fun deleteRecipeTagFromRecipe(recipeId: Int) {
        recipeTagDao?.getRecipeTagsFromRecipe(recipeId)?.forEach {
            recipeTagDao?.delete(it)
        }
    }
}