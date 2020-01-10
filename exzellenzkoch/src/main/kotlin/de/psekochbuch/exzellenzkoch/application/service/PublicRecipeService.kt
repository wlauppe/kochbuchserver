package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import de.psekochbuch.exzellenzkoch.infrastructure.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Service
//@Component
@Transactional
class PublicRecipeService
{
    @Autowired var publicRecipeDao:PublicRecipeDao? = null
    @Autowired var userDao:UserDao? = null

    fun getRecipe(id:Int) : PublicRecipeDto
    {
        var recipe = publicRecipeDao?.findById(id)
        return convertRecipeToDto(recipe?.get())

    }

    fun addRecipe(publicRecipe: PublicRecipeDto?) {
        var userId:String;
        if(publicRecipe?.userId == null)
        {
            return;
        }
        else
        {
            userId = publicRecipe.userId
        }
        val user = userDao?.findById(userId)?.get()
        publicRecipeDao?.save(PublicRecipe(0, publicRecipe!!.title , publicRecipe?.ingredientsText, publicRecipe.preparationDescription,publicRecipe.picture, publicRecipe.cookingTime, publicRecipe.preperationTime, user, publicRecipe.createtionDate,false, publicRecipe.portions, null))
    }

    private fun convertRecipeToDto(recipe:PublicRecipe?) : PublicRecipeDto
    {
        //var bao:ByteArrayOutputStream = ByteArrayOutputStream()

        //ImageIO.write(recipe?.picture, "jpg", bao)
       /* if(recipe?.picture == null)
        {
            recipe?.picture = "0".toByteArray(Charsets.UTF_8)
        }*/
        var recipeChapter = publicRecipeDao?.getChapterFromRecipe(recipe!!.recipeId)

        return PublicRecipeDto(recipe!!.recipeId, recipe.title, recipe.ingredientsText, recipe.preparationDescription, recipe?.picture, recipe.cookingTime, recipe.preparationTime, recipe.user?.userId, recipe.creationDate, recipe.portions, 0, recipeChapter)
    }

    private fun convertDtoToRecipe(recipe:PublicRecipeDto)
    {
        return PublicRecipe(recipe.id,recipe.title,recipe.ingredientsText,recipe.preparationDescription, recipe.picture,recipe.cookingTime,recipe.preperationTime,recipe.)
    }
}