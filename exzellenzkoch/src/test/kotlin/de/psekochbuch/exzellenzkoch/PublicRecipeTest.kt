package de.psekochbuch.exzellenzkoch

//import org.junit.runner.RunWith

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.firebase.auth.FirebaseAuth
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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.util.*
import kotlin.collections.ArrayList


//@TestPropertySource(locations = ["classpath:test.properties"])
@ExtendWith(SpringExtension::class)
@SpringBootTest
//@RunWith(SpringRunner::class)
//@DataJpaTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class PublicRecipeTest {

    @Autowired
    var recipedao : PublicRecipeDao? = null

    @Autowired
    val publicRecipeController : PublicRecipeController? = null

    private val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYzZTllYThmNzNkZWExMTRkZWI5YTY0OTcxZDJhMjkzN2QwYzY3YWEiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiVGVzdCIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9leHplbGxlbnprb2NoIiwiYXVkIjoiZXh6ZWxsZW56a29jaCIsImF1dGhfdGltZSI6MTU4MTg3Mjc1MCwidXNlcl9pZCI6IjViVm0yMkNQbmtYTHJQUmxhNlRIck0zQlZvVDIiLCJzdWIiOiI1YlZtMjJDUG5rWExyUFJsYTZUSHJNM0JWb1QyIiwiaWF0IjoxNTgxODcyNzUwLCJleHAiOjE1ODE4NzYzNTAsImVtYWlsIjoidGVzdEB0ZXN0LmRlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3RAdGVzdC5kZSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.v2j_0OP_o8G7upsQle2Womy5yIiBTHKQeqmSa1nDQkoVu1fmcGS4ARggVm2Zi53bgrdNvcmGGuBIF3XjA6WmNqytMDiKrQr7HY7-Iuam8Rf4MRElU9_s3IPnsOzD16Sqr4AOLSt01Tun5QGLDmBIB4RiiwWbT1BEZ5aFJKCVYeo5hibhQUj9QJIh1iGc_EJ9WvXtknDsTSsiIUNccut6sZkMfQHKRx5ufG4Qm-55a_JzaD422O_YRp4W41KrtSAbMPxFZs5tRA4PGiGkubOkOiefqL17J6J8kcA_T_igWck8b4pJEjTAFg1KSHKzkfiBLknNKvZ9QlBgR7Z5k0outw"

    @Autowired
    private val mvc :MockMvc? = null

    companion object {
        private var id: Int = 0

        private val newRec = PublicRecipeDto(0, "Salat", "#Zutaten:\n200 g Eisbergsalat\n50 g Tomaten\n70 g Mais","Man wirft alles in eine Schüssel", "", 12,12,"test2","2019-12-31 00:00:00",4,0,
                listOf(IngredientChapterDto(0,"Zutaten", listOf(IngredientDto(0,"Eisbergsalat", 200.0,"g"), IngredientDto(0,"Tomaten", 50.0, "g"), IngredientDto(0, "Mais", 70.0,"g")))), listOf(RecipeTagDto("Salat"), RecipeTagDto("gesund")))

        private val searchRecipes = ArrayList<PublicRecipeDto>()

        private var dynRecipes = ArrayList<Int>()
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
    fun addRecipe()
    {
        val json = asJsonString(newRec)
        /*val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.post("/api/recipes").content(json).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))?.andReturn() as MvcResult//andExpect(status().isOk)*/

        val recipe = publicRecipeController?.addRecipe(newRec)

        //result.response.status
        //val recipe :PublicRecipeDto = asJsonObject(result.response.contentAsString) as PublicRecipeDto
        if(recipe != null) {
            Assertions.assertTrue(recipe.id > 0)
            newRec.id = recipe.id
            id = recipe.id
            Assertions.assertEquals(newRec , recipe)
        } else
        {
            Assertions.fail("Recipe could not created")
        }
        /*val values = recipedao?.search("%Win%", null,null,null, 1, 10)
        val t = ""
        Assertions.assertThat(values).isNotEmpty*/
    }

    @Test
    @Order(2)
    fun getRecipe()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/recipes/$id"))?.andReturn() as MvcResult

        if(id != 0) {
            val loadRec = asJsonObject(result.response.contentAsString, PublicRecipeDto::class.java)
            if (loadRec is PublicRecipeDto) {
                val recipe = publicRecipeController?.getRecipe(id)
                Assertions.assertEquals(recipe, loadRec)
            }
        }
    }

    @Test
    @Order(3)
    fun updateRecipe()
    {
        if(newRec.id != 0)
        {
            newRec.title = "Eisbergsalat"
            publicRecipeController?.updateRecipe(newRec,newRec.id)
            val updatedRec = publicRecipeController?.getRecipe(newRec.id)
            org.junit.jupiter.api.Assertions.assertEquals(newRec.title, updatedRec?.title)
        }
    }

    @Test
    @Order(4)
    fun deleteRecipe()
    {
        if(newRec.id != 0) {
            publicRecipeController?.deleteRecipe(newRec.id)

            Assertions.assertThrows(de.psekochbuch.exzellenzkoch.domain.exceptions.ResourceNotFoundException::class.java) {
                publicRecipeController?.getRecipe(newRec.id)
            }

            //org.junit.jupiter.api.Assertions.assertNull(delRec)
        }
    }

    @Test
    @Order(5)
    fun searchTitle()
    {
        for(i in 0..3)
        {
            val rec = publicRecipeController?.addRecipe(newRec)
            if(rec != null) dynRecipes.add(rec.id)
        }
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/recipes?title=Test&page=1&readCount=10"))?.andReturn() as MvcResult

        var recipes = asJsonArrayObject(result.response.contentAsString, Array<PublicRecipeDto>::class.java)

        if(recipes is Array<*>)
        {
            recipes = recipes.toList()
            recipes.forEach {
                if (it is PublicRecipeDto) {
                    Assertions.assertTrue(it.title.matches(Regex("(?i:(.)*(Test).*)")))
                } else {
                    Assertions.fail("Did not load recipes")
                }
            }
        } else {
            Assertions.fail("Did not load recipes")
        }

        //recipes.
        //org.junit.jupiter.api.Assertions.assertTrue(Rege)
    }

    @Test
    @Order(6)
    fun searchIngredient()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/recipes?title=&tags=&ingredients=Eier&ingredients=Nudeln&ingredients=Salz&page=1&readCount=10"))?.andReturn() as MvcResult

        var recipes = asJsonArrayObject(result.response.contentAsString, Array<PublicRecipeDto>::class.java)

        if(recipes is Array<*>)
        {
            recipes = recipes.toList()
            recipes.forEach { recipeItem ->
                if(recipeItem is PublicRecipeDto)
                {
                    var isCorrect = false
                    recipeItem.ingredientsChapter?.forEach {chapter ->
                        chapter.ingredient?.forEach {ingredient->
                            if(ingredient.nameIngredient == "Nudeln" || ingredient.nameIngredient == "Eier" || ingredient.nameIngredient == "Salz")
                            {
                                isCorrect = true
                            }
                        }
                    }
                    Assertions.assertTrue(isCorrect)
                }
            }
        }
    }

    @Test
    @Order(7)
    fun searchTag()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/recipes?title=&tags=Salat&tags=gesund&ingredients=&page=1&readCount=10"))?.andReturn() as MvcResult

        var recipes = asJsonArrayObject(result.response.contentAsString, Array<PublicRecipeDto>::class.java)

        if(recipes is Array<*>)
        {
            recipes = recipes.toList()
            recipes.forEach {recipeItem ->
                if(recipeItem is PublicRecipeDto)
                {
                    val recipeTag = recipeItem.recipeTag
                    if(recipeTag != null)
                    {
                        if(recipeTag.contains(RecipeTagDto("Salat")) || recipeTag.contains(RecipeTagDto("gesund")))
                        {
                            Assertions.assertTrue(true)
                        } else
                        {
                            Assertions.fail()
                        }
                    }
                }
            }
        }
        dynRecipes.forEach {
            recipedao?.deleteById(it)
        }
        dynRecipes = ArrayList<Int>()
    }

    @Test
    @Order(8)
    fun getRecipesFromUser()
    {
        createRecipes()

        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/recipes/user/test2"))?.andReturn() as MvcResult

        var recipes = asJsonArrayObject(result.response.contentAsString, Array<PublicRecipeDto>::class.java)
        if(recipes is Array<*>)
        {
            recipes = recipes.toList()
            recipes.forEach {recipe->
                if(recipe is PublicRecipeDto)
                {
                    Assertions.assertEquals("test2",recipe.userId)
                }
            }
        }
        deleteRecipes()

    }

    private fun createRecipes() {
        for(i in 0..3)
        {
            recipedao?.addRecipe("Salat", "#Zutaten\n10 g Salat", "Man macht was", "",3,3,"test2", Date(System.currentTimeMillis()),4)
            val id = recipedao?.getLastId()
            if(id != null)
                dynRecipes.add(id)
        }
    }

    private fun deleteRecipes()
    {
        dynRecipes.forEach {
            recipedao?.deleteById(it)
        }
    }

    private fun asJsonString(obj: Any?): String {
        return try {
            val mapper = ObjectMapper()
            mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun asJsonObject(obj: String, type: Class<out Any>): Any {
        return try {
            val mapper = ObjectMapper()
            mapper.registerModule(KotlinModule())
            mapper.readValue(obj, type)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun asJsonArrayObject(obj: String, type:Class<out Any>): Any {
        return try {
            val mapper = ObjectMapper()
            mapper.registerModule(KotlinModule())
            mapper.readValue(obj, type)
            //listOf(mapper.readValue(obj, type))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}