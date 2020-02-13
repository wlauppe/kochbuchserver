package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.UserApi
import de.psekochbuch.exzellenzkoch.application.dto.CustomTokenDto
import de.psekochbuch.exzellenzkoch.application.dto.UserDto
import de.psekochbuch.exzellenzkoch.application.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

/**
 * Implementation from the UserApi
 */
@RestController
class UserController : UserApi
{
    @Autowired
    private var service:UserService? = null

    override fun createUser(userId: String) :CustomTokenDto?{
        return service?.createUser(userId)
    }

    override fun createUser(): CustomTokenDto? {
        return service?.createUser("")
    }

    override fun updateUser(user: UserDto, userId: String) {
        service?.updateUser(user, userId)
    }

    override fun deleteUser(userId: String) {
        service?.deleteUser(userId)
    }

    override fun getUser(userId: String): UserDto? {
        return service?.getUser(userId)
    }

    override fun checkUser(userId: String): UserDto? {
        return service?.checkUser(userId)
    }

    override fun reportRecipe(id: String) {
        service?.reportUser(id)
    }

}