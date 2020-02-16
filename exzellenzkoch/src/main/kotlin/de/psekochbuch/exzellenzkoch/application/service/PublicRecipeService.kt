package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
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
    @Autowired private var userDao : UserDao? = null

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
    fun addRecipe(publicRecipe: PublicRecipeDto?) :PublicRecipeDto? {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == publicRecipe?.userId) {
        if(publicRecipe?.userId == null) return null

        publicRecipeDao?.addRecipe(publicRecipe.title,publicRecipe.ingredientsText,publicRecipe.preparationDescription, publicRecipe.picture, publicRecipe.cookingTime, publicRecipe.preparationTime, publicRecipe.userId, RecipeConverter.convertStringToDate(publicRecipe.creationDate), publicRecipe.portions)
        publicRecipeDao?.flush()

        val recipeId = publicRecipeDao?.getLastId() ?: return null

        val chapterDto = createChapterWithIngredients(publicRecipe, recipeId)


        publicRecipe.recipeTag?.forEach{
            recipeTagDao?.addRecipeTag(it.name ,recipeId)
        }

        return PublicRecipeDto(recipeId,publicRecipe.title,publicRecipe.ingredientsText,publicRecipe.preparationDescription,publicRecipe.picture,publicRecipe.cookingTime,publicRecipe.preparationTime,publicRecipe.userId,publicRecipe.creationDate,publicRecipe.portions,publicRecipe.ratingAvg,chapterDto,publicRecipe.recipeTag)
        }
        return null
    }

    /**
     * Delete the recipe with the specific id
     * @param id The id from the recipe
     */
    fun deleteRecipe(id: Int) {
        val publicRecipe = publicRecipeDao?.findById(id)?.get()
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        val displayName = FirebaseAuth.getInstance().getUser(auth.principal as String).displayName
        if(displayName == publicRecipe?.user?.userId || userDao?.isAdmin(displayName) == 1)
        {
            //deleteChapterWithIngredients(id)
            //deleteRecipeTagFromRecipe(id)
            publicRecipeDao?.deleteById(id)
            publicRecipeDao?.flush()
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
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == publicRecipe?.userId) {
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
            deleteRecipeTagFromRecipe(publicRecipe.id)

            publicRecipe.recipeTag?.forEach{
                recipeTagDao?.addRecipeTag(it.name ,publicRecipe.id)
            }
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
        val t:String? = if(!title.isEmpty && title.get() != "") '%' + title.get() + '%' else null
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
     * Create Chapters with there ingredients in the database and convert the chapters in DTOs
     * @param publicRecipe the recipe form the ingredients and chapters
     * @param recipeId the id from the recipe from the database
     */
    private fun createChapterWithIngredients(publicRecipe: PublicRecipeDto, recipeId: Int) : MutableList<IngredientChapterDto>?
    {
        val chapterDto:MutableList<IngredientChapterDto> = ArrayList()
        publicRecipe.ingredientsChapter?.forEach { chapter ->

            ingredientChapterDao?.addChapter(recipeId,chapter.name)
            ingredientChapterDao?.flush()
            val chapterId = ingredientChapterDao?.getLastId() ?: return null
            chapter.id = chapterId
            chapterDto.add(chapter)
            chapter.ingredient?.forEach{
                ingredientAmountDao?.addIngredient(chapterId, it.nameIngredient,it.amount, it.unit)
                ingredientAmountDao?.flush()
            }
        }
        return chapterDto
    }

    /**
     * Delete chapters and ingredients from a recipe
     * @param recipeId The id from the recipe
     */
    private fun deleteChapterWithIngredients(recipeId:Int)
    {

        ingredientChapterDao?.getChapterIdsFromRecipe(recipeId)?.forEach{
            ingredientAmountDao?.deleteIngredientsFromChapter(it)
            ingredientAmountDao?.flush()

            ingredientChapterDao?.deleteById(it)
            ingredientChapterDao?.flush()

        }

    }

    /**
     * Delete tags from a recipe
     * @param recipeId The id from the recipe
     */
    private fun deleteRecipeTagFromRecipe(recipeId: Int) {
        recipeTagDao?.getRecipeTagsFromRecipe(recipeId)?.forEach {
            recipeTagDao?.delete(it)
            recipeTagDao?.flush()
        }
    }

    fun getUserRecipes(userId: String): List<PublicRecipeDto> {
        val recipes:MutableList<PublicRecipeDto> = ArrayList()
        publicRecipeDao?.getRecipesFromUser(userId)?.forEach {recipe ->
            recipes.add(RecipeConverter.convertRecipeToDto(recipe,ingredientChapterDao,recipeTagDao,ingredientAmountDao))

        }
        return  recipes
    }
}