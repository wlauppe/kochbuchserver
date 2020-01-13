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
@Table(name= "public_Recipe")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class PublicRecipe(

        @Id
        //@Column(name = "recipe_Id")
        //@GeneratedValue(strategy = GenerationType.AUTO)
        var recipeId :Int,

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
        @JoinColumn(name = "user_Id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val user:User?,


        @NotBlank
        var creationDate:Date,

        @NotBlank
        var markAsEvil:Boolean,

        @NotBlank
        var portions:Int,

       @ManyToMany(mappedBy = "favourites")
        var userFav:List<User>?
        ) :Serializable
{}