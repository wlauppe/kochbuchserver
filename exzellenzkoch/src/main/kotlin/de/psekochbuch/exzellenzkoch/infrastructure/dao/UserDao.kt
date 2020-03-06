package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.User
import de.psekochbuch.exzellenzkoch.infrastructure.dao.custom.UserRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Offer database actions for user
 */
@Repository
interface UserDao : JpaRepository<User?,String?>, UserRepository
{
    @Query("SELECT user_Id FROM user WHERE email = (:email)", nativeQuery = true)
    fun getUserIdByEmail(email:String): String

    @Query("INSERT INTO user (user_Id, email, description, mark_As_Evil) VALUES ((:userId), (:email), (:description), 0)", nativeQuery = true)
    fun createUser(userId:String, email: String, description:String)

    @Query("UPDATE user SET user_Id = (:newUserId), user.description = (:description) WHERE user.user_Id = (:oldUserId) ", nativeQuery = true)
    fun updateUser(newUserId:String, oldUserId:String, description: String)

    /**
     * Set the report value of an user
     * @param id Id of the user
     */
    @Query("UPDATE user SET mark_as_Evil = 1 WHERE user_Id = (:id)", nativeQuery = true)
    fun reportUser(id: String)

    /**
     * Set the report value false of an user
     * @param id Id of the user
     */
    @Query("UPDATE user SET mark_as_Evil = 0 WHERE user_Id = (:id)", nativeQuery = true)
    fun deReportUser(id: String)

    @Query("SELECT user_Id FROM user WHERE user_Id REGEXP 'KochDummy([0-9]*)$' ORDER BY LENGTH(user_Id) DESC, user_Id DESC LIMIT 1", nativeQuery = true)
    fun getCountTmpUser() :String?

    @Query("SELECT COUNT(user_Id) FROM user WHERE user_Id = (:id) AND isAdmin = 1", nativeQuery = true)
    fun isAdmin(id: String?): Int

    @Query("INSERT INTO favourites (recipe_Id, user_Id) VALUES ((:recipeId), (:userId))", nativeQuery = true)
    fun addFavourite(recipeId:Int, userId: String)

    @Query("DELETE FROM favourites WHERE user_Id = (:userId) AND recipe_Id = (:recipeId)", nativeQuery = true)
    fun deleteFavourite(recipeId: Int, userId: String)
}