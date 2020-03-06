package de.psekochbuch.exzellenzkoch

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.controller.AdminController
import de.psekochbuch.exzellenzkoch.application.controller.PublicRecipeController
import de.psekochbuch.exzellenzkoch.application.controller.UserController
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.RecipeTagDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.User
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

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AdminTest {

    @Autowired
    val adminController:AdminController? = null

    @Autowired
    val recipeController:PublicRecipeController? = null

    @Autowired
    val userController:UserController? = null

    @Autowired
    val recipeDao:PublicRecipeDao? = null

    @Autowired
    val userDao:UserDao? = null

    companion object
    {
        val recipes = ArrayList<Int>()
        val reporteUserIds = ArrayList<String>()
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
    fun isAdmin()
    {
        Assertions.assertTrue(userController?.isAdmin()!!.isAdmin)
    }

    @Test
    @Order(2)
    fun reportRecipe() {
        val newRec = PublicRecipeDto(0, "Salat", "#Zutaten:\n200 g Eisbergsalat\n50 g Tomaten\n70 g Mais", "Man wirft alles in eine Schüssel", "", 12, 12, "test2", "2019-12-31 00:00:00", 4, 0,
                listOf(IngredientChapterDto(0, "Zutaten", listOf(IngredientDto(0, "Eisbergsalat", 200.0, "g"), IngredientDto(0, "Tomaten", 50.0, "g"), IngredientDto(0, "Mais", 70.0, "g")))), listOf(RecipeTagDto("Salat")))


        for (i in 1..3) {
            newRec.title = newRec.title + i
            val recipe = recipeController?.addRecipe(newRec)
            if (recipe != null) {
                recipes.add(recipe.id)
            }
        }
        recipes.forEach {
            recipeController?.reportRecipe(it)
        }

        recipes.forEach {
            val rec = recipeDao?.findById(it)?.get()
            Assertions.assertTrue(rec!!.markAsEvil)
        }
    }

    @Test
    @Order(3)
    fun reportUser() {

        for (i in 0..3) {
            userDao?.createUser("ReportUser$i", "email@email.de", "Ich in ein Report")
            reporteUserIds.add("ReportUser$i")
        }
        reporteUserIds.forEach {
            userController?.reportUser(it)
        }
        reporteUserIds.forEach{
            val user = userDao?.findById(it)?.get()
            Assertions.assertTrue(user!!.markAsEvil)
        }
    }

    @Test
    @Order(4)
    fun getReportRecipes()
    {
        adminController?.getReportedPublicRecipe(1,10)?.forEach {
            val recipe = recipeDao?.findById(it.id)?.get()
            if(recipe != null) {
                Assertions.assertTrue(recipe.markAsEvil)
            }
            else {
                Assertions.fail("reported recipes are null")
            }
        }
    }

    @Test
    @Order(5)
    fun getReportedUser()
    {
        adminController?.getReportedUser(1,10)?.forEach {
            val user = userDao?.findById(it.userId)?.get()
            if(user != null)
            {
                Assertions.assertTrue(user.markAsEvil)
            } else {
                Assertions.fail("reported user are null")
            }
        }
    }

    @Test
    @Order(6)
    fun deReportRecipe() {
        recipes.forEach {

            adminController?.deReportPublicRecipe(it)
        }
        recipes.forEach {
            val rec = recipeDao?.findById(it)?.get()
            Assertions.assertFalse(rec!!.markAsEvil)
            recipeDao?.deleteById(it)
        }
    }

    @Test
    @Order(7)
    fun deReportUser()
    {
        reporteUserIds.forEach {

            adminController?.deReportUser(it)
        }
        reporteUserIds.forEach {
            val user = userDao?.findById(it)?.get()
            Assertions.assertFalse(user!!.markAsEvil)
            userDao?.deleteById(it)
        }
    }
}