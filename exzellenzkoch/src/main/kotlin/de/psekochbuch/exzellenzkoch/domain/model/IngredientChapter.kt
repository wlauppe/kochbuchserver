package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Dataclass for ingredientchapter
 */
@Entity
@Table(name= "ingredientChapter")
@EntityListeners(AuditingEntityListener::class)
data class IngredientChapter(
        /**
         * Id of the chapters
         */
        @Id
        val chapterId:Int,

        /**
         * The recipe of the chapter
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipe_Id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe:PublicRecipe?,

        /**
         * Name of the chapter
         */
        @NotBlank
        var chapterName:String
)