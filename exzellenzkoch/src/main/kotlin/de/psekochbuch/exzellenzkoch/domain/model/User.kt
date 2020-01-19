package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import javax.persistence.*

/**
 * Dataclass for user
 * It represent the table in the database
 */
@Entity
@Table(name= "user")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class User(
        /**
         * The name and id of an user
         */
        @Id
        var userId:String,

        /**
         * Description over an user
         */
        var description:String,

       //@ManyToMany(cascade = [ CascadeType.ALL ], mappedBy = "members", targetEntity = Group::class)
       //var groups:List<Group>?,

        /**
         * The favourite recipes from a user
         */
        @ManyToMany(cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "Favourites",
                joinColumns = [JoinColumn(name = "userId")] ,
                inverseJoinColumns = [JoinColumn(name = "recipeId")]
        )
        var favourites:List<PublicRecipe>,

        /**
         * If the user is reported then the variable is true
         */
        var markAsEvil:Boolean


) :Serializable