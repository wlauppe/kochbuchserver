package de.psekochbuch.exzellenzkoch.application.dto

data class UserDto(
        val userId :String,

        val email:String,

        val description:String,

        val markAsEvil : Boolean
)