package de.psekochbuch.exzellenzkoch.application.dto

import java.util.*

data class PublicRecipeDto(
        val id:Int,

        var title:String,

        var ingredientsText:String,

        var preparationDescription:String,

        var picture:ByteArray,

        var cookingTime:Int,

        var preparationTime:Int,

        val userId:String?,

        var creationDate:Date,

        var portions:Int,

        var ratingAvg:Int,

        var ingredientsChapter: List<IngredientChapterDto>?,

        var recipeTag:List<RecipeTagDto>?

)