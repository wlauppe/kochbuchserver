package de.psekochbuch.exzellenzkoch.infrastructure.dao

import de.psekochbuch.exzellenzkoch.domain.model.IngredientAmount
import de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.domain.model.RecipeTag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.criteria.Root


@Repository
interface PublicRecipeDao : JpaRepository<PublicRecipe?, Int?>
{
    @Query("SELECT new de.psekochbuch.exzellenzkoch.domain.model.IngredientChapter(t.chapter_Id, null, t.chapter_Name) FROM ingredient_chapter u WHERE u.recipe_Id = 1", nativeQuery = true)
    fun getChapterFromRecipe(id:Int): List<IngredientChapter>

    @Query("SELECT p FROM ingredient_amount p WHERE chapter_id = (:id)", nativeQuery = true)
    fun getIngredientsFromChapter(id:Int) : List<IngredientAmount>

    @Query("SELECT p FROM recipe_tag p WHERE recipe_Id = (:id)", nativeQuery = true)
    fun getRecipeTagsFromRecipe(id:Int): List<RecipeTag>

    @Query("INSERT INTO public_recipe (title, ingredients_Text, preparation_Description, picture, cooking_Time, preparation_Time, user_Id, creation_Date, portions, mark_As_Evil) VALUES ((:title), (:ingr_Text), (:prep_Desc), (:pic), (:cook_T), (:prep_T), (:user), (:cre_D), (:port), 0)", nativeQuery = true)
    fun addRecipe(title:String, ingr_Text:String, prep_Desc:String, pic:ByteArray, cook_T:Int, prep_T:Int, user:String, cre_D: Date, port:Int)


    @Query("SELECT LAST_INSERT_ID()", nativeQuery = true)
    fun getLastId(): Int

    @Query(":query", nativeQuery = true)
    fun search(@Param ("query") query:Any): List<PublicRecipe>



    //@Query("SELECT p FROM publicRecipe p where recipe_id =(:id)", nativeQuery = true)
    //fun getRecipe(@Param("id") id:Int) :List<PublicRecipe>
}

class UserRepositoryCustomImpl : UserRepositoryCustom {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    fun findUserByEmails(emails: Set<String?>): List<User> {
        val cb = entityManager!!.criteriaBuilder
        val query: CriteriaQuery<User> = cb.createQuery(User::class.java)
        val user: Root<User> = query.from(User::class.java)
        val emailPath: Path<String> = user.get<Any>("email")
        val predicates: MutableList<Predicate> = ArrayList<Predicate>()
        for (email in emails) {
            predicates.add(cb.like(emailPath, email))
        }
        query.select(user)
                .where(cb.or(predicates.toArray(arrayOfNulls<Predicate>(predicates.size))))
        return entityManager.createQuery<Any>(query)
                .resultList
    }
}