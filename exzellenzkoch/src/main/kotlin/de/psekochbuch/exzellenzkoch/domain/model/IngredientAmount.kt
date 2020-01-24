package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Dataclass for the ingredients
 * It represent the table in the database
 */
@Entity
@Table(name= "ingredientAmount")
@EntityListeners(AuditingEntityListener::class)
data class IngredientAmount(

        /**
         * The chapter of the ingredient
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "chapterId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val chapter: IngredientChapter?,

        /**
         * Name of the ingredient
         */
        @Id
        @NotBlank
        var nameIngredient:String,

        /**
         * The amount of the ingredient
         */
        var amount:Double,

        /**
         * The unit of the amount from the ingredient
         */
        var unit:String
)