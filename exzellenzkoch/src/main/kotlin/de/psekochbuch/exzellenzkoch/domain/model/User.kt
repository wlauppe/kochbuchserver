package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name= "user")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class User(
        @Id
        var userId:String,

        var description:String,

       //@ManyToMany(cascade = [ CascadeType.ALL ], mappedBy = "members", targetEntity = Group::class)
       //var groups:List<Group>?,

        @ManyToMany(cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "Favourites",
                joinColumns = [JoinColumn(name = "userId")] ,
                inverseJoinColumns = [JoinColumn(name = "recipeId")]
        )
        var favourites:List<PublicRecipe>,

        var markAsEvil:Boolean


) :Serializable