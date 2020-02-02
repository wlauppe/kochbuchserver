package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Offer database actions for user
 */
@Repository
interface UserDao : JpaRepository<User?,String?>
{
    @Query("SELECT user_Id WHERE email = (:email)", nativeQuery = true)
    fun getUserIdByEmail(email:String): String

    @Query("INSERT INTO User (user_Id, email, description, mark_As_Evil) VALUES ((:userId), (:email), (:description), 0)", nativeQuery = true)
    fun createUser(userId:String, email: String, description:String)
}