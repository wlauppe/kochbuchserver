package de.psekochbuch.exzellenzkoch

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.controller.AdminController
import de.psekochbuch.exzellenzkoch.application.controller.PublicRecipeController
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.RecipeTagDto
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
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
    val recipeDao:PublicRecipeDao? = null

    @BeforeEach
    fun configureSecurity() {
        val user = FirebaseAuth.getInstance().getUser("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03")
        val token = user.userMetadata
        val authenticationToken: Authentication = FirebaseAuthentication("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03", FirebaseTokenHolder(null, user), null)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    @Test
    @Order(1)
    fun reportRecipe() {
        val newRec = PublicRecipeDto(0, "Salat", "#Zutaten:\n200 g Eisbergsalat\n50 g Tomaten\n70 g Mais", "Man wirft alles in eine Schüssel", "", 12, 12, "test2", "2019-12-31 00:00:00", 4, 0,
                listOf(IngredientChapterDto(0, "Zutaten", listOf(IngredientDto(0, "Eisbergsalat", 200.0, "g"), IngredientDto(0, "Tomaten", 50.0, "g"), IngredientDto(0, "Mais", 70.0, "g")))), listOf(RecipeTagDto("Salat")))
        var recipes = ArrayList<PublicRecipeDto>()

        for (i in 1..3) {
            newRec.title = newRec.title + i
            val recipe = recipeController?.addRecipe(newRec)
            if (recipe != null) {
                recipes.add(recipe)
            }
        }
        recipes.forEach {
            recipeController?.reportRecipe(it.id)
        }

        recipes.forEach {
            val recipe = recipeDao?.findById(it.id)?.get()
            if(recipe != null) {
                Assertions.assertTrue(recipe.markAsEvil)
            }
        }
    }

    @Test
    @Order(2)
    fun reportUser()
}