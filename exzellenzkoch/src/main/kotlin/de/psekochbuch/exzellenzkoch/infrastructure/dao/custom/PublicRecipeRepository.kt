package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import java.util.*

interface PublicRecipeRepository
{
    fun search(title:String?, tags:List<String>?, ingredients:List<String>?,
               creationDate: Date?, pageNumber:Int, count:Int) : MutableList<Any?>?
}