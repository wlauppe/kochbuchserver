package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*

@Entity
@Table(name= "user")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class User(
        @Id
        var userId:String,

        var description:String,

        @ManyToMany(mappedBy = "members")
        var groups:List<Group>,

        @ManyToMany(cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "Favourites",
                joinColumns = [JoinColumn(name = "userId")] ,
                inverseJoinColumns = [JoinColumn(name = "recipeId")]
        )
        var favourites:List<PublicRecipe>,

        var markAsEvil:Boolean
)