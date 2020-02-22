package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired

class FavouritesService {

    @Autowired
    val userDao:UserDao? = null

    fun addFavRecipe(userId: String, recipeId: Int)
    {

    }

    fun getFavRecipes(userId: String, page: Int, readCount: Int): List<PublicRecipeDto>
    {

    }

    fun delFavRecipe(userId: String, recipeId: Int)
    {

    }
}