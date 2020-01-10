package de.psekochbuch.exzellenzkoch.domain.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name= "group")
@EntityListeners(AuditingEntityListener::class)
data class Group(
        @Id
        val groupId:Int,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "mainUser", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        val mainUser:User,

        @NotBlank
        var name:String,

        @ManyToMany(cascade = [ CascadeType.ALL ])
        @JoinTable(
                name = "UserGroup",
                joinColumns = [JoinColumn(name = "groupId")] ,
                inverseJoinColumns = [JoinColumn(name = "userId")]
        )
        var members:List<User>,

        @NotBlank
        var markAsEvil:Boolean

)
