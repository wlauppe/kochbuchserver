package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.psekochbuch.exzellenzkoch.application.dto.AdminDto
import de.psekochbuch.exzellenzkoch.application.dto.CustomTokenDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.domain.exceptions.ResourceNotFoundException
import de.psekochbuch.exzellenzkoch.infrastructure.UserChecker
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


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
     * @param userId The userid of the user
     * @return return a customer token, which is a token with claims
     */
    fun createUser(userId: String) : CustomTokenDto? {

        val fireAuth: FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication

        return CustomTokenDto(UserChecker.createUser(fireAuth.principal as String, userDao))

    }

    /**
     * Update an user with the new 
     */
    fun updateUser(user: UserDto, userId: String) {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.getUserIdByEmail((fireAuth.credentials as FirebaseTokenHolder).email!!) == userId) {
            userDao?.findById(user.userId)?.map {dbUser ->
                dbUser?.userId = user.userId
                dbUser?.description = user.description
                if(dbUser != null) userDao?.save(dbUser)
            }
            if(userId != user.userId) {
                updateFirebaseDisplayName(fireAuth.principal as String, user.userId)
            }
        }

    }

    fun deleteUser(userId: String) {
        val fireAuth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if (userDao?.getUserIdByEmail((fireAuth.credentials as FirebaseTokenHolder).email!!) == userId)
        {
            userDao?.deleteById(userId)
        }
    }

    fun getUser(userId: String): UserDto? {

        var user :UserDto? = UserDto("", "", "")
        val userDb =  userDao?.findById(userId)?.orElseThrow { ResourceNotFoundException("User", "id", userId) }
        if (userDb != null) {
            user = UserDto(userDb.userId, "", userDb.description)
        }
        return user
    }

    fun checkUser(userId: String): UserDto? {
        if(loadUser(userId))
        {
            return UserDto(userId, "","")
        }
        return UserDto("", "", "")
    }


    private fun loadUser(userId:String) :Boolean {
        val done = CountDownLatch(1)
        var exist = false

        val database = FirebaseDatabase.getInstance().reference
        database.child("user").orderByChild("userId").equalTo(userId).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
                done.countDown()
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                if (snapshot != null && snapshot.value != null){
                    exist = true

                }
                done.countDown()
            }

        })

        try {
            done.await(20,TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return exist
    }

    fun reportUser(userId: String) {
        userDao?.reportUser(userId)
    }

    fun isAdmin(): AdminDto {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(userDao?.isAdmin(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName) == 1) {
            return AdminDto(true)
        }
        return AdminDto(false)
    }

    private fun updateFirebaseDisplayName(uId:String ,userId:String)
    {
        val update : UserRecord.UpdateRequest = UserRecord.UpdateRequest(uId)
        update.setDisplayName(userId)
        FirebaseAuth.getInstance().updateUser(update)
    }
}