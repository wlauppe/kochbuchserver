package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.dto.CustomTokenDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.NumberFormatException

/**
 * Class for management of user
 */
@Service
@Transactional
class UserService {

    @Autowired
    private var userDao:UserDao? = null

    /**
     * Create an user on the DB and create a custom token with claims for the user
     */
    fun createUser(userId: String) : CustomTokenDto? {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val claim : MutableMap<String, Any> = HashMap()
        claim["normalUser"] = true

        var id = ""
        id = if(userId == "")
        {
            createUniqueUserId()
        } else {
            userId
        }

        userDao?.createUser(id, (fireAuth.credentials as FirebaseTokenHolder).email, "")

        return CustomTokenDto(auth.createCustomToken(fireAuth.principal as String, claim))
        //auth.setCustomUserClaims(fireAuth.principal as String, claim)
    }

    private fun createUniqueUserId(): String {
        val dummy = userDao?.getCountTmpUser()
        if(dummy != null)
        {
            return try {
                val count = dummy.substring(0, "KochDummy".length).toInt()
                "KochDummy" + (count + 1)

            }catch (ex : NumberFormatException) {
                ""
            }
        }
        return ""
    }

    /**
     * Update an user with the new 
     */
    fun updateUser(user: UserDto, userId: String) {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.getUserIdByEmail((fireAuth.credentials as FirebaseTokenHolder).email) == userId) {
            userDao?.findById(user.userId)?.map {dbUser ->
                dbUser?.userId = user.userId
                dbUser?.description = user.description
                if(dbUser != null) userDao?.save(dbUser)
            }
        }

    }

    fun deleteUser(userId: String) {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if (userDao?.getUserIdByEmail((fireAuth.credentials as FirebaseTokenHolder).email) == userId)
        {
            userDao?.deleteById(userId)
        }
    }

    fun getUser(userId: String): UserDto? {
        var user :UserDto? = UserDto("", description = "")
        userDao?.findById(userId)?.map { dbUser ->
            user = UserDto(dbUser!!.userId, description = dbUser.description)
        }
        return user
    }

    fun reportUser(userId: String) {
        userDao?.reportUser(userId)
    }
}