package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.User
import de.psekochbuch.exzellenzkoch.infrastructure.dao.*
import org.springframework.beans.factory.annotation.Autowired
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
        val recipes:MutableList<PublicRecipeDto> = ArrayList()
        publicRecipeDao?.getReportedRecipes(page,readCount)?.forEach {
            if(it is PublicRecipe)
            {
                recipes.add(RecipeConverter.convertRecipeToDto(it,ingredientChapterDao,recipeTagDao,ingredientAmountDao))
            }
        }
        return recipes
    }

    fun getReportedUser(page: Int, readCount: Int): List<UserDto>
    {
        val user:MutableList<UserDto> = ArrayList()

        userDao?.getReportedUser(page,readCount)?.forEach {
            if(it is User)
            {
                user.add(UserDto(it.userId,"",it.description))
            }
        }
        return user
    }

    fun deReportPublicRecipe(recipeId: Int)
    {
        publicRecipeDao?.deReportRecipe(recipeId)
    }

    fun deReportUser(userId:String)
    {
        userDao?.deReportUser(userId)
    }
}