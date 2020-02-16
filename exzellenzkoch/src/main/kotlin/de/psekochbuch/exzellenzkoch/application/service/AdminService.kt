package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.User
import de.psekochbuch.exzellenzkoch.infrastructure.dao.*
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * Class for management of public recipes
 */
@Service
@Transactional
class AdminService {

    @Autowired
    private val publicRecipeDao : PublicRecipeDao? = null
    @Autowired
    private val ingredientChapterDao: IngredientChapterDao? = null
    @Autowired
    private val recipeTagDao: RecipeTagDao? = null
    @Autowired
    private val ingredientAmountDao: IngredientDao? = null
    @Autowired
    private val userDao:UserDao? = null

    fun getReportedPublicRecipe(page: Int, readCount: Int) :List<PublicRecipeDto>
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.isAdmin(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName) == 1) {
            val recipes: MutableList<PublicRecipeDto> = ArrayList()
            publicRecipeDao?.getReportedRecipes(page, readCount)?.forEach {
                if (it is PublicRecipe) {
                    recipes.add(RecipeConverter.convertRecipeToDto(it, ingredientChapterDao, recipeTagDao, ingredientAmountDao))
                }
            }
            return recipes
        }
        return emptyList()
    }

    fun getReportedUser(page: Int, readCount: Int): List<UserDto>
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.isAdmin(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName) == 1) {
            val user: MutableList<UserDto> = ArrayList()

            userDao.getReportedUser(page, readCount)?.forEach {
                if (it is User) {
                    user.add(UserDto(it.userId, "", it.description))
                }
            }
            return user
        }
        return emptyList()
    }

    fun deReportPublicRecipe(recipeId: Int)
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.isAdmin(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName) == 1) {
            publicRecipeDao?.deReportRecipe(recipeId)
        }
    }

    fun deReportUser(userId:String)
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.isAdmin(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName) == 1) {
            userDao.deReportUser(userId)
        }
    }
}