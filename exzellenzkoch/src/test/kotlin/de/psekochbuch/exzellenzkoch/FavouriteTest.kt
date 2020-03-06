package de.psekochbuch.exzellenzkoch

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.controller.FavouritesController
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.RecipeTagDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import kotlin.collections.ArrayList

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FavouriteTest {

    @Autowired
    val favouritesController : FavouritesController? = null

    @Autowired
    val recipeDao:PublicRecipeDao? = null

    @Autowired
    val userDao:UserDao? = null

    companion object{
        val recipes = ArrayList<Int>()

        private val newRec = PublicRecipeDto(0, "Salat", "#Zutaten:\n200 g Eisbergsalat\n50 g Tomaten\n70 g Mais","Man wirft alles in eine Schüssel", "", 12,12,"test2","2019-12-31 00:00:00",4,0,
                listOf(IngredientChapterDto(0,"Zutaten", listOf(IngredientDto(0,"Eisbergsalat", 200.0,"g"), IngredientDto(0,"Tomaten", 50.0, "g"), IngredientDto(0, "Mais", 70.0,"g")))), listOf(RecipeTagDto("Salat")))
    }

    @BeforeEach
    fun configureSecurity() {
        val user = FirebaseAuth.getInstance().getUser("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03")
        val token = user.userMetadata
        val authenticationToken: Authentication = FirebaseAuthentication("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03", FirebaseTokenHolder(null, user), null)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    @Test
    @Order(1)
    fun addFavourite() {
        createRecipes()
        recipes.forEach {
            favouritesController?.addFavRecipe("test2", it)
        }
        userDao?.getFavFromUser("test2",1,10)?.forEach {
            if(it is PublicRecipe)
            {
                Assertions.assertTrue(recipes.contains(it.recipeId))
            }
        }
    }

    @Test
    @Order(2)
    fun getFavRecipes()
    {
        favouritesController?.getFavRecipes("test2", 1, 10)
        userDao?.getFavFromUser("test2",1,10)?.forEach {
            if(it is PublicRecipe)
            {
                Assertions.assertTrue(recipes.contains(it.recipeId))
            }
        }
    }

    @Test
    @Order(3)
    fun deleteFav()
    {
        recipes.forEach{
            favouritesController?.delFavRecipe("test2", it)
        }
        val fav = userDao?.getFavFromUser("test2", 1, 10)
        if(fav != null)
        {
            Assertions.assertTrue(fav.isEmpty())
        }
        else {
            Assertions.fail()
        }
    }

    private fun createRecipes() {
        for(i in 0..3)
        {
            recipeDao?.addRecipe("Salat", "#Zutaten\n10 g Salat", "Man macht was", "",3,3,"test2", Date(System.currentTimeMillis()),4)
            val id = recipeDao?.getLastId()
            if(id != null)
                recipes.add(id)
        }
    }
}