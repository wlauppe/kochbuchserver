package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import java.util.*

interface PublicRecipeRepository
{
    fun search(title:String?, tags:List<String>?, ingredients:List<String>?,
               creationDate: Date?)
}