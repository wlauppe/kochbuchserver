package de.psekochbuch.exzellenzkoch.application.dto

data class UserDto(
        val userId :String,

        val imageUrl:String ="",

        val description:String
)