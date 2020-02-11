package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.AdminApi
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.application.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

/**
 * Implements the AdminApi
 */
@Repository
class AdminController : AdminApi {

    @Autowired
    private val service:AdminService? = null

    override fun getReportedPublicRecipe(page: Int, readCount: Int): List<PublicRecipeDto>? {
        return service?.getReportedPublicRecipe(page,readCount)
    }

    override fun getReportedUser(page: Int, readCount: Int): List<UserDto>? {
        return service?.getReportedUser(page,readCount)
    }

    override fun deReportPublicRecipe(recipeId: Int) {
        service?.deReportPublicRecipe(recipeId)
    }

    override fun deReportUser(userId: String) {
        service?.deReportUser(userId)
    }
}