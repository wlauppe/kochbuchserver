package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class PublicRecipeRepositoryImpl : PublicRecipeRepository
{
    @PersistenceContext
    private val entityManager: EntityManager? = null

    override fun search(title: String?, tags: List<String>?, ingredients: List<String>?, creationDate: Date?, pageNumber:Int, count:Int) : MutableList<Any?>? {
        var query:String = "SELECT p.recipe_id, title, ingredients_Text, preparation_Description, picture, cooking_Time, preparation_Time, user_Id, creation_Date, portions, mark_As_Evil " +
                "FROM public_recipe p LEFT JOIN ingredient_chapter c ON p.recipe_Id = c.recipe_Id " +
                "LEFT JOIN ingredient_amount a ON c.chapter_Id = a.chapter_Id " +
                "LEFT JOIN recipe_tag t ON p.recipe_Id = t.recipe_Id "
        if(title != null || tags != null || ingredients != null || creationDate  != null) query += "WHERE "

        if(title != null) query += "p.title LIKE (:title) "; var hasCriteriaSet = true
        if(ingredients != null)
            query += if(hasCriteriaSet) "AND a.name_Ingredient IN (:ingredients) "
            else " a.name_Ingredient IN (:ingredients)"; hasCriteriaSet = true
        if(tags != null)
            query += if(hasCriteriaSet) "AND t.tag_Id IN (:tags)" else " t.tag_Id IN (:tags) " ; hasCriteriaSet = true

        if(creationDate != null)
            query += if(hasCriteriaSet) "AND p.creation_Date = (:creationDate) " else " p.creation_Date = (:creationDate) "
        query += "GROUP BY recipe_id ORDER BY creation_Date ASC LIMIT " + ((pageNumber -1)* count) + "," + count + ";"

        val nativeQuery = entityManager?.createNativeQuery(query, PublicRecipe::class.java)

        if(title != null) nativeQuery?.setParameter("title", title)
        if(ingredients != null) nativeQuery?.setParameter("ingredients", ingredients)
        if(tags != null) nativeQuery?.setParameter("tags", tags)
        if(creationDate != null) nativeQuery?.setParameter("creationDate", creationDate)
        //nativeQuery?.maxResults = count
        //nativeQuery?.firstResult = (pageNumber-1) * count

        return nativeQuery?.resultList
    }


}