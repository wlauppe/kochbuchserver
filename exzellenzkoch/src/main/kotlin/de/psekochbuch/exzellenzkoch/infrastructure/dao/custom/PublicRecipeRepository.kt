package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import java.util.*

/**
 * Interface for a custom repository
 */
interface PublicRecipeRepository
{
    /**
     * Search after recipes with specific title, tags, ingredients and creationdate
     * @param title The title which should search
     * @param tags The tags from the recipes
     * @param ingredients The ingredients from the recipes
     * @param page The page of the already loaded recipes
     * @param readCount Count of the to loaded recipes
     * @return the loaded recipes
     */
    fun search(title:String?, tags:List<String>?, ingredients:List<String>?,
               creationDate: Date?, pageNumber:Int, count:Int) : MutableList<Any?>?

    fun getReportedRecipes(pageNumber: Int, count: Int) :MutableList<Any?>?
}