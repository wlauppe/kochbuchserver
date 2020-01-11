package de.psekochbuch.exzellenzkoch.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name= "group")
@EntityListeners(AuditingEntityListener::class)
@JsonIgnoreProperties(allowGetters = true)
data class Group(
        @Id
        val groupId:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "mainUser", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val mainUser:User,

        @NotBlank
        var name:String,

        @ManyToMany(targetEntity = User::class ,cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "user_group",
                joinColumns = [JoinColumn(name = "group_Id")] ,
                inverseJoinColumns = [JoinColumn(name = "user_Id")]
        )
        var members:List<User>?,

        @NotBlank
        var markAsEvil:Boolean

) :Serializable
