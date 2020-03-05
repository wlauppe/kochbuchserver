package de.psekochbuch.exzellenzkoch

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.controller.FileController
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class FileTests {

    private val token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjYzZTllYThmNzNkZWExMTRkZWI5YTY0OTcxZDJhMjkzN2QwYzY3YWEiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiVGVzdCIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9leHplbGxlbnprb2NoIiwiYXVkIjoiZXh6ZWxsZW56a29jaCIsImF1dGhfdGltZSI6MTU4MTgxNjkyNiwidXNlcl9pZCI6IjViVm0yMkNQbmtYTHJQUmxhNlRIck0zQlZvVDIiLCJzdWIiOiI1YlZtMjJDUG5rWExyUFJsYTZUSHJNM0JWb1QyIiwiaWF0IjoxNTgxODE2OTI2LCJleHAiOjE1ODE4MjA1MjYsImVtYWlsIjoidGVzdEB0ZXN0LmRlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbInRlc3RAdGVzdC5kZSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.sgZv-AhYlDUYKfwNE39I9ffyFjJpWUgwtCG0Lo6JxlHueiqM6XDaSfxAzq1FmLWvTkmwdNlPLXNQlc-rXQ1z5BtNPhK36b9Vr8U2NS5__q8QSFC55ibqxEUqajAtRciD4h9iVdkZ8VeMy3KluhPFCP9vgtk6u1vVqATDEoM7mCbUDP4JH0qPUAepbNjLpYsN-CWAzeOaUxqqniG-B5EqZhGGEBF9sqtrnBcOHKIKxGhbiaGPM0tRDNk87wBP9bKnvs-VQbEAe05DtcN4ebFX2sByok5f4QubfhjjxkscLMkIazPC3C-INPo233luorH6szuNxCeE691vZzhGU1nxNw"

    /*@Autowired
    val wContext: WebApplicationContext? = null

    @Autowired
    private val mvc : MockMvc? = null*/

    @Autowired
    val fileController:FileController? = null

    @BeforeEach
    fun configureSecurity() {
        val user = FirebaseAuth.getInstance().getUser("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03")
        val token = user.userMetadata
        val authenticationToken: Authentication = FirebaseAuthentication("ZVQtHQgMTYf1ZrbLQqQJbgFnZm03", FirebaseTokenHolder(null, user), null)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    @Test
    @Order(1)
    fun addImage()
    {
        val file = File("test.jpg")
        val bo = file.exists()

        var content :ByteArray? = null
        try {
            content = Files.readAllBytes(Paths.get(file.absolutePath))
        } catch (ex : IOException)
        {

        }
        val multipartFile = MockMultipartFile(file.name,file.name,MediaType.parseMediaType("*/*").type, content)

        val fileDto = fileController?.addImage(multipartFile)
        if(fileDto?.filePath != null) {
            val editPath = fileDto.filePath.substring("api/".length, fileDto.filePath.length)
            Assertions.assertTrue(File(editPath).exists())
        }
        //val f = MockMultipartFile(file.name,file.inputStream())

        //val mock = MockMvcBuilders.webAppContextSetup(wContext!!).alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print()).build()

        //mock.perform(MockMvcRequestBuilders.multipart("/api/images").file(f).contentType(MediaType.parseMediaType("*/*")).header("Authorization", token)).andExpect(status().isOk)

        //val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.post("/api/recipes").content(json).contentType(MediaType.APPLICATION_JSON)
        //        .header("Authorization", token))?.andReturn() as MvcResult
    }

    @Test
    @Order(2)
    fun addImageWithSameName()
    {
        val file = File("test.jpg")
        val bo = file.exists()

        var content :ByteArray? = null
        try {
            content = Files.readAllBytes(Paths.get(file.absolutePath))
        } catch (ex : IOException)
        {

        }
        val multipartFile = MockMultipartFile(file.name,file.name,MediaType.parseMediaType("*/*").type, content)

        val fileDto = fileController?.addImage(multipartFile)

        if(fileDto?.filePath != null)
        {
            var editPath = fileDto.filePath.substring("api/".length, fileDto.filePath.length)
            val createdFile = File(editPath)
            Assertions.assertTrue(createdFile.exists())
            editPath = createdFile.nameWithoutExtension.substring("Test".length, createdFile.name.length)
            Assertions.assertNotEquals("",editPath)
        }

    }

    @Test
    @Order(3)
    fun getImage()
    {
        
    }

}