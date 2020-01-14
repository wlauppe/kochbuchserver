package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.PublicRecipeApi
import de.psekochbuch.exzellenzkoch.application.dto.IngredientChapterDto
import de.psekochbuch.exzellenzkoch.application.dto.IngredientDto
import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.application.service.PublicRecipeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import java.util.*
import kotlin.collections.ArrayList

@RestController
class PublicRecipeController :PublicRecipeApi
{
    @Autowired
    var service:PublicRecipeService? = null

    override fun getRecipe(id: Int) : PublicRecipeDto? {
       return service?.getRecipe(id)
    }

    override fun addRecipe(publicRecipe: PublicRecipeDto?) {
        service?.addRecipe(publicRecipe)
    }

    override fun updateRecipe(publicRecipe: PublicRecipeDto?, id: Int) {
        service?.updateRecipe(publicRecipe, id)
    }

    override fun deleteRecipe(id: Int) {
        service?.deleteRecipe(id)
    }

}