package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

/**
 * Dataclass for comments
 * It represent the table in the database
 */
@Entity
@Table(name= "comment")
@EntityListeners(AuditingEntityListener::class)
data class Comment(
        /**
         * Id of the comment
         */
        @Id
        val commentId:Int,

        /**
         * The user which creates the comment
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,

        /**
         * The recipe which has the comment
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "recipeId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val recipe: PublicRecipe,

        /**
         * The comment text
         */
        var comment:String,

        /**
         * The date of the comment, when it was created
         */
        val date:Date,

        /**
         * If the comment is reported then the variable is true
         */
        var markAsEvil:Boolean
)