package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

interface UserRepository {

    fun getReportedUser(pageNumber: Int, count: Int) :MutableList<Any?>?
}