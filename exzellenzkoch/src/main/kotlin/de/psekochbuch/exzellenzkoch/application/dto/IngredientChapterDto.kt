package de.psekochbuch.exzellenzkoch.application.dto

data class IngredientChapterDto(
        var id:Int,

        var name:String,
        
        var ingredient:List<IngredientDto>?
)