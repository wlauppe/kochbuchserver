package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.User
import org.springframework.data.jpa.repository.Query
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class UserRepositoryImpl : UserRepository {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    override fun getReportedUser(pageNumber: Int, count: Int): MutableList<Any?>? {

        val query:String = "SELECT * FROM user WHERE mark_as_Evil = 1 ORDER BY user_Id ASC LIMIT " + ((pageNumber -1)* count) + "," + count + ";"

        val nativeQuery = entityManager?.createNativeQuery(query, User::class.java)

        return nativeQuery?.resultList


    }

    override fun getFavFromUser(userId :String, page: Int, readCount:Int) : MutableList<Any?>?
    {
        val query:String = "SELECT p.recipe_id, title, ingredients_Text, preparation_Description, picture, cooking_Time, preparation_Time, user_Id, creation_Date, portions, mark_As_Evil " +
                "FROM public_recipe p LEFT JOIN ingredient_chapter c ON p.recipe_Id = c.recipe_Id " +
                "LEFT JOIN ingredient_amount a ON c.chapter_Id = a.chapter_Id " +
                "LEFT JOIN recipe_tag t ON p.recipe_Id = t.recipe_Id " +
                "WHERE p.recipe_Id IN (SELECT recipe_Id FROM favourites WHERE user_id = '"+ userId +"') GROUP BY p.recipe_Id ORDER BY creation_Date ASC LIMIT "+ ((page -1)* readCount) +","+ readCount + ";"

        val nativeQuery = entityManager?.createNativeQuery(query, PublicRecipe::class.java)

        return nativeQuery?.resultList
    }


}