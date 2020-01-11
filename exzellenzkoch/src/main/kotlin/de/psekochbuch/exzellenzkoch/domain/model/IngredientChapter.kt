package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name= "ingredientChapter")
@EntityListeners(AuditingEntityListener::class)
data class IngredientChapter(
        @Id
        val chapterId:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe:PublicRecipe?,

        @NotBlank
        var chapterName:String
)