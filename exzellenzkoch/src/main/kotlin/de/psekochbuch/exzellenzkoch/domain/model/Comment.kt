package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@Table(name= "comment")
@EntityListeners(AuditingEntityListener::class)
data class Comment(
        @Id
        val commentId:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe: PublicRecipe,

        var comment:String,

        val date:Date,

        var markAsEvil:Boolean
)