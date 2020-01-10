package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.awt.image.BufferedImage
import java.util.*
import javax.persistence.*

@Entity
@Table(name= "sharedPrivateRecipe")
@EntityListeners(AuditingEntityListener::class)
data class SharedPrivateRecipe(
        @Id
        val recipeId:Int,

        var title:String,

        var ingredientsText:String,

        var preparationTime:Int,

        var preparationDescription:String,

        @Column(columnDefinition = "BLOB")
        var picture:ByteArray,

        var cookingTime:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user: User,

        val creationDate:Date,

        var markAsEvil:Boolean,

        var portions:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "groupId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        var group:Group
)