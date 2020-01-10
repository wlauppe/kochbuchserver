package de.psekochbuch.exzellenzkoch.application.dto

data class IngredientDto(
        var chapterId:Int,

        var nameIngredient:String,

        var amount:Int,

        var unit:String
)