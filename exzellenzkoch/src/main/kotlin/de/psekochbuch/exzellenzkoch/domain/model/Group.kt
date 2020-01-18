package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * Dataclass for Groups
 */
@Entity
@Table(name= "group")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class Group(
        /**
         * The id of the group
         */
        @Id
        val groupId:Int,

        /**
         * The creator of this group
         */
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "mainUser", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val mainUser:User,

        /**
         * The name of the group
         */
        @NotBlank
        var name:String,

        /**
         * The list of user, where are in the group
         */
        @ManyToMany(targetEntity = User::class ,cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "user_group",
                joinColumns = [JoinColumn(name = "group_Id")] ,
                inverseJoinColumns = [JoinColumn(name = "user_Id")]
        )
        var members:List<User>?,

        /**
         * If the group is reported then the variable is true
         */
        @NotBlank
        var markAsEvil:Boolean

) :Serializable
