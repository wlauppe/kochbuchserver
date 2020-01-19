package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

/**
 * Dataclass of the recipe tag
 * It represent the table in the database
 */
@Entity
@Table(name= "recipeTag")
@EntityListeners(AuditingEntityListener::class)
data class RecipeTag(
        /**
         * The name and id of the tag
         */
        @Id
        var tagId:String,

        /**
         * The recipe which has this tag
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe:PublicRecipe
)