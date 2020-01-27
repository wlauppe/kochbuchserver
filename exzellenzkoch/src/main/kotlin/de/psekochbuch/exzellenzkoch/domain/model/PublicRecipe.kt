package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Dataclass for public recipes
 * It represent the table in the database
 */
@Entity
@Table(name= "public_Recipe")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class PublicRecipe(

        /**
         *Id of the recipe
         */
        @Id
        var recipeId :Int,

        /**
         * Title of the recipe
         */
        @NotBlank
        var title:String,

        /**
         * All ingredients as a textline
         */
        @NotBlank
        var ingredientsText:String,

        /**
         * Description how to create a meal
         */
        @NotBlank
        var preparationDescription:String,

        /**
         * Picture of the meal
         */
        var picture: String,

        /**
         * Time to cook a meal
         */
        @NotBlank
        var cookingTime:Int,

        /**
         * Time to preparate a meal
         */
        @NotBlank
        var preparationTime:Int,

        /**
         * The user who create the recipe
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_Id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user:User?,

        /**
         * Date when the recipe was created
         */
        @NotBlank
        var creationDate:Date,

        /**
         * If the recipe is reported then the variable is true
         */
        @NotBlank
        var markAsEvil:Boolean,

        /**
         * Count of portions for people
         */
        @NotBlank
        var portions:Int,

        /**
         * Connection to users who have this recipe as favorite
         */
       @ManyToMany(mappedBy = "favourites")
        var userFav:List<User>?
        ) :Serializable
{}