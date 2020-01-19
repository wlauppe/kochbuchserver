package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.awt.image.BufferedImage
import java.util.*
import javax.persistence.*

/**
 * Dataclass of the shared private recipe
 * It represent the table in the database
 */
@Entity
@Table(name= "sharedPrivateRecipe")
@EntityListeners(AuditingEntityListener::class)
data class SharedPrivateRecipe(

        /**
         *Id of the recipe
         */
        @Id
        val recipeId:Int,

        /**
         * Title of the recipe
         */
        var title:String,

        /**
         * All ingredients as a textline
         */
        var ingredientsText:String,

        /**
         * Time to preparate a meal
         */
        var preparationTime:Int,

        /**
         * Description how to create a meal
         */
        var preparationDescription:String,

        /**
         * Picture of the meal
         */
        @Column(columnDefinition = "BLOB")
        var picture:ByteArray,

        /**
         * Time to cook a meal
         */
        var cookingTime:Int,

        /**
         * The user who create the recipe
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,

        /**
         * Date when the recipe is created
         */
        val creationDate:Date,

        /**
         * If the recipe is reported then the variable is true
         */
        var markAsEvil:Boolean,

        /**
         * Count of portions for people
         */
        var portions:Int,

        /**
         * Connection to group who has this recipe
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "groupId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        var group:Group
)