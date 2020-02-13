package de.psekochbuch.exzellenzkoch.infrastructure

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseTokenHolder
import org.springframework.beans.factory.annotation.Autowired

object UserChecker
{
    fun createUser(token:FirebaseTokenHolder, userDao: UserDao?) : String
    {
        val auth : FirebaseAuth = FirebaseAuth.getInstance()
        val claim : MutableMap<String, Any> = HashMap()
        claim["normalUser"] = true

        val user = auth.getUser(token.uid)


        var id = user.displayName
        if(id == null || id == "")
        {
            id = createUniqueUserId(userDao)
            val databaseReference = FirebaseDatabase.getInstance().reference
            databaseReference.child("user").push().setValue(UserDto(id,"", ""), DatabaseReference.CompletionListener { databaseError, databaseReference ->
                //do nothing
            })
        }

        userDao?.createUser(id, user.email, "")

        return auth.createCustomToken(user.uid, claim)
    }

    private fun createUniqueUserId(userDao: UserDao?): String {
        val dummy :String? = userDao?.getCountTmpUser()
        if(dummy != null)
        {
            return try {
                var count = 0
                if(dummy != "KochDummy") count = dummy.substring(0, "KochDummy".length).toInt()
                "KochDummy" + (count + 1)

            }catch (ex : NumberFormatException) {
                ""
            }
        }
        return "KochDummy"
    }
}