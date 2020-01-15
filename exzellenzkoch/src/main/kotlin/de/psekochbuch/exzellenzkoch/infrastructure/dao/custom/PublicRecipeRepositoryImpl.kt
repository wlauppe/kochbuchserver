package de.psekochbuch.exzellenzkoch.infrastructure.dao.custom

import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

class PublicRecipeRepositoryImpl : PublicRecipeRepository
{
    @PersistenceContext
    private val entityManager: EntityManager? = null

    override fun search(title: String?, tags: List<String>?, ingredients: List<String>?, creationDate: Date?) {
        var query:String = "SELECT * FROM public_recipe p, ingredient_chapter c, ingredient_amount a, recipe_tag t" +
                    "WHERE p.recipe_Id = c.recipe_Id" +
                    "AND c.chapter_Id = a.chapter_Id" +
                    "AND t.recipe_Id = p.recipe_Id"

            if(title != null) query + "AND p.title LIKE = " + title
            if(ingredients != null) query + "AND a.name_Ingredient IN "
            if(tags != null) query + "AND t.tag_Id LIKE "
            if(creationDate != null) query + "AND p.creation_Date = "
        query + ";"
        entityManager?.createNamedQuery(query)?.resultList
    }


}