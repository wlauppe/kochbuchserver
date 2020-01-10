package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.awt.image.BufferedImage
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name= "publicRecipe")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class PublicRecipe(
        @Id
        val recipeId :Int,

        @NotBlank
        var title:String,

        @NotBlank
        var ingredientsText:String,

        @NotBlank
        var preparationDescription:String,

        @Column(columnDefinition = "BLOB")
        var picture: ByteArray,

        @NotBlank
        var cookingTime:Int,

        @NotBlank
        var preparationTime:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "userId", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user:User,
        val userId:String,

        @NotBlank
        var creationDate:Date,

        @NotBlank
        var markAsEvil:Boolean,

        @NotBlank
        var portions:Int,

       @ManyToMany(mappedBy = "favourites")
        var userFav:List<User>
        ) :Serializable
{}