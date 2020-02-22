package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.IngredientChapterDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.IngredientDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.RecipeTagDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Class for management of favourites
 */
@Service
@Transactional
class FavouritesService {

    @Autowired
    private val userDao:UserDao? = null
    @Autowired
    private val recipeChapterDao:IngredientChapterDao? = null
    @Autowired
    private val recipeTagDao: RecipeTagDao? =null
    @Autowired
    private val ingredientDao:IngredientDao? = null

    fun addFavRecipe(userId: String, recipeId: Int)
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == userId) {
            userDao?.addFavourite(recipeId,userId)
        }
    }

    fun getFavRecipes(userId: String, page: Int, readCount: Int): List<PublicRecipeDto>
    {
        val recipes : MutableList<PublicRecipeDto> = ArrayList()
        userDao?.getFavFromUser(userId,page,readCount)?.forEach {
            if(it is PublicRecipe) {
                recipes.add(RecipeConverter.convertRecipeToDto(it, recipeChapterDao, recipeTagDao, ingredientDao))
            }
        }

        return recipes
    }

    fun delFavRecipe(userId: String, recipeId: Int)
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == userId) {
            userDao?.deleteFavourite(recipeId,userId)
        }
    }
}