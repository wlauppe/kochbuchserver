package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

/**
 * Dataclass of the private recipe tags
 */
@Entity
@Table(name= "privateRecipeTag")
@EntityListeners(AuditingEntityListener::class)
data class PrivateRecipeTag(
        /**
         * Name and id of the tag
         */
        @Id
        var tagId:String,

        /**
         * The shared private recipe of the tag
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe:SharedPrivateRecipe
)