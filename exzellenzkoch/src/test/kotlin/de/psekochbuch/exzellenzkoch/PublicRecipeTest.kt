package de.psekochbuch.exzellenzkoch

//import org.junit.runner.RunWith
import com.fasterxml.jackson.databind.ObjectMapper
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.RecipeTagDto
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


//@TestPropertySource(locations = ["classpath:test.properties"])
@SpringBootTest
//@RunWith(SpringRunner::class)
//@DataJpaTest
@AutoConfigureMockMvc
class PublicRecipeTest {

    @Autowired
    var recipedao : PublicRecipeDao? = null

    private val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYzZTllYThmNzNkZWExMTRkZWI5YTY0OTcxZDJhMjkzN2QwYzY3YWEiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiVGVzdCIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9leHplbGxlbnprb2NoIiwiYXVkIjoiZXh6ZWxsZW56a29jaCIsImF1dGhfdGltZSI6MTU4MTgxNjkyNiwidXNlcl9pZCI6IjViVm0yMkNQbmtYTHJQUmxhNlRIck0zQlZvVDIiLCJzdWIiOiI1YlZtMjJDUG5rWExyUFJsYTZUSHJNM0JWb1QyIiwiaWF0IjoxNTgxODE2OTI2LCJleHAiOjE1ODE4MjA1MjYsImVtYWlsIjoidGVzdEB0ZXN0LmRlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3RAdGVzdC5kZSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.sgZv-AhYlDUYKfwNE39I9ffyFjJpWUgwtCG0Lo6JxlHueiqM6XDaSfxAzq1FmLWvTkmwdNlPLXNQlc-rXQ1z5BtNPhK36b9Vr8U2NS5__q8QSFC55ibqxEUqajAtRciD4h9iVdkZ8VeMy3KluhPFCP9vgtk6u1vVqATDEoM7mCbUDP4JH0qPUAepbNjLpYsN-CWAzeOaUxqqniG-B5EqZhGGEBF9sqtrnBcOHKIKxGhbiaGPM0tRDNk87wBP9bKnvs-VQbEAe05DtcN4ebFX2sByok5f4QubfhjjxkscLMkIazPC3C-INPo233luorH6szuNxCeE691vZzhGU1nxNw"

    @Autowired
    private val mvc :MockMvc? = null

    @Test
    fun addRecipe()
    {
        val json = asJsonString(PublicRecipeDto(0, "Kuchen", "Test","Backe Backe Kuchen", "", 12,12,"Test","2019-12-31 00:00:00",4,0,
                listOf(IngredientChapterDto(0,"TestChapter", listOf(IngredientDto(0,"IngredientName", 9.0,"g")))), listOf(RecipeTagDto("TestTag"))))
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.post("/api/recipes").content(json).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))?.andReturn() as MvcResult//andExpect(status().isOk)

        result.response.status
        val recipe :PublicRecipeDto = asJsonObject(result.response.contentAsString) as PublicRecipeDto

        
        /*val values = recipedao?.search("%Win%", null,null,null, 1, 10)
        val t = ""
        Assertions.assertThat(values).isNotEmpty*/
    }


    private fun asJsonString(obj: Any?): String {
        return try {
            val mapper = ObjectMapper()
            mapper.writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun asJsonObject(obj: String): Any {
        return try {
            val mapper = ObjectMapper()
            mapper.readValue(obj, Any::class.java)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}