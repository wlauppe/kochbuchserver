package de.psekochbuch.exzellenzkoch.application.dto

data class UserDto(
        val userId :String,

        val imageUrl:String ="",

        val description:String
)
{
    override fun equals(other: Any?): Boolean {
        if(other is UserDto)
        {
            if(other.userId != userId) return false
            if(other.imageUrl != imageUrl) return false
            if(other.description != description) return false
            return true
        }
        return false
    }
}