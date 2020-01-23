package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
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
    private var userdao:UserDao? = null

    fun createUser(user: UserDto) {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        if(userdao?.getUserIdByEmail((fireAuth.credentials as FirebaseTokenHolder).email) == user.userId) {

            val claim : MutableMap<String, Any> = HashMap()
            claim["normalUser"] = true
            auth.setCustomUserClaims(fireAuth.principal as String, claim)
        }
    }

    fun updateUser(user: UserDto, userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteUser(userId: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getUser(userId: String): UserDto {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}