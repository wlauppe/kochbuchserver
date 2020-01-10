package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name= "recipeTag")
@EntityListeners(AuditingEntityListener::class)
data class RecipeTag(
        @Id
        var tagId:String,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe:PublicRecipe
)