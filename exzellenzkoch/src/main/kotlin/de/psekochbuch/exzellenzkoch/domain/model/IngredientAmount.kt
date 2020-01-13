package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name= "ingredientAmount")
@EntityListeners(AuditingEntityListener::class)
data class IngredientAmount(


        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "chapterId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val chapter: IngredientChapter?,

        @Id
        @NotBlank
        var nameIngredient:String,

        var amount:Int,

        var unit:String
)