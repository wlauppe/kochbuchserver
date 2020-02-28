package de.psekochbuch.exzellenzkoch

import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.RecipeTagDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserDaoTest {

    @Autowired
    val userDao:UserDao? = null
    @Autowired
    val recipeDao:PublicRecipeDao? = null

    @Test
    fun getFavFromUser()
    {
        val userId = "admin"
        val page = 1
        val readCount = 10

        val recipe = userDao?.getFavFromUser(userId,page,readCount)



        //userDao?.deleteFavourite(4, "admin")






        val t = ""
    }
}