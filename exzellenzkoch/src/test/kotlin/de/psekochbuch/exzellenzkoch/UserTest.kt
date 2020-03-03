package de.psekochbuch.exzellenzkoch

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.database.*
import de.psekochbuch.exzellenzkoch.application.controller.UserController
import de.psekochbuch.exzellenzkoch.application.dto.CustomTokenDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.infrastructure.UserChecker
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.RecipeTagDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserTest {

    @Autowired
    val userDao:UserDao? = null

    @Autowired
    val userController:UserController? = null

    @Autowired
    private val mvc : MockMvc? = null

    @BeforeEach
    fun configureSecurity() {
        val user = FirebaseAuth.getInstance().getUser("XciGvAb8deP5qwVlOnM9mco0a073")
        val token = user.userMetadata
        val authenticationToken: Authentication = FirebaseAuthentication("XciGvAb8deP5qwVlOnM9mco0a073", FirebaseTokenHolder(null, user), null)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    @Test
    @Order(1)
    fun createUserWithoutUserId()
    {
        val userToken :CustomTokenDto? = userController?.createUser()
        Assertions.assertNotNull(userToken?.customToken)
        val user = FirebaseAuth.getInstance().getUser("XciGvAb8deP5qwVlOnM9mco0a073")
        val dbUser = userDao?.findById(user.displayName)
        Assertions.assertNotNull(dbUser?.get())

        val database = FirebaseDatabase.getInstance().reference
        database.child("user").orderByChild("userId").equalTo(user.displayName).addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {

            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                val userId = snapshot?.value
            }

        })
        if(dbUser != null) {
            userDao?.delete(dbUser.get())
            updateFirebaseDisplayName("")
        }

    }

    @Test
    @Order(2)
    fun userNotExist()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/users/check/TestUser"))?.andReturn() as MvcResult

        val user = asJsonObject(result.response.contentAsString, UserDto::class.java) as UserDto

        Assertions.assertEquals("", user.userId)
    }

    @Test
    @Order(3)
    fun createUserWithId()
    {
        val userToken: CustomTokenDto? = userController?.createUser("TestUser")
        Assertions.assertNotNull(userToken?.customToken)
        val user = FirebaseAuth.getInstance().getUser("XciGvAb8deP5qwVlOnM9mco0a073")
        val dbUser = userDao?.findById(user.displayName)
        Assertions.assertNotNull(dbUser?.get())

    }

    @Test
    @Order(4)
    fun userExist()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/users/check/TestUser"))?.andReturn() as MvcResult

        val user = asJsonObject(result.response.contentAsString, UserDto::class.java) as UserDto

        Assertions.assertNotEquals("", user.userId)
    }

    @Test
    @Order(5)
    fun getUserByUserId()
    {
        val result : MvcResult = mvc?.perform(MockMvcRequestBuilders.get("/api/users/TestUser"))?.andReturn() as MvcResult

        val user = asJsonObject(result.response.contentAsString, UserDto::class.java) as UserDto
        Assertions.assertEquals(UserDto("TestUser", "", ""), user)

    }

    @Test
    @Order(6)
    fun updateUser()
    {
        val desc = "Test Desc"
        userController?.updateUser(UserDto("NeuTest","", desc), "TestUser")
        val user = userDao?.findById("NeuTest")
        Assertions.assertNotNull(user)
        Assertions.assertEquals(desc,user?.get()?.description)
        Assertions.assertEquals("NeuTest", user?.get()?.userId)
    }

    @Test
    @Order(7)
    fun deleteUser()
    {

            userController?.deleteUser("NeuTest")
            updateFirebaseDisplayName("")

    }

    @Test
    @Order(8)
    fun getFavFromUser()
    {
        val database = FirebaseDatabase.getInstance().reference
        database.child("user").orderByChild("userId").equalTo("admin").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {

            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.value
            }

        })



        val userId = "admin"
        val page = 1
        val readCount = 10

        val recipe = userDao?.getFavFromUser(userId,page,readCount)



        //userDao?.deleteFavourite(4, "admin")
        val t = ""
    }

    private fun updateFirebaseDisplayName(userId:String)
    {
        val update : UserRecord.UpdateRequest = UserRecord.UpdateRequest("XciGvAb8deP5qwVlOnM9mco0a073")
        update.setDisplayName(userId)
        FirebaseAuth.getInstance().updateUser(update)
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
}