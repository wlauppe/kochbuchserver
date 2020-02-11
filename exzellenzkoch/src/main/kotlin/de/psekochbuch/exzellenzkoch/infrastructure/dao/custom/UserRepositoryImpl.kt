package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import de.psekochbuch.exzellenzkoch.domain.model.User
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


}