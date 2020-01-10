package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.PublicRecipeApi
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.service.PublicRecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class PublicRecipeController :PublicRecipeApi
{
    @Autowired
    var service:PublicRecipeService? = null

    override fun getRecipe(id: Int) : PublicRecipeDto? {
        return service?.getRecipe(id)
    }

    override fun addRecipe(publicRecipe: PublicRecipeDto) {
        return service?.addRecipe(publicRecipe)
    }

}