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

/**
 * Class for management of user
 */
@Service
@Transactional
class UserService {

    @Autowired
    private var userDao:UserDao? = null

    fun createUser(user: UserDto) : CustomTokenDto? {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val claim : MutableMap<String, Any> = HashMap()
        claim["normalUser"] = true

        userDao?.createUser(user.userId, user.email, user.description)

        return CustomTokenDto(auth.createCustomToken(fireAuth.principal as String, claim))
        //auth.setCustomUserClaims(fireAuth.principal as String, claim)
    }

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
        var user :UserDto? = null
        userDao?.findById(userId)?.map { dbUser ->
            user = UserDto(dbUser!!.userId, dbUser.email, dbUser.description,dbUser.markAsEvil)
        }
        return user
    }

    fun reportUser(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}