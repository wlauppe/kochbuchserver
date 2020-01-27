package de.psekochbuch.exzellenzkoch.application.dto

import java.util.*

/**
 * Data Transfer Object of the public recipe
 */
data class PublicRecipeDto(
        /**
         * Id of the recipe
         */
        val id:Int,

        /**
         * Title of the recipe
         */
        var title:String,

        /**
         * Ingredientstext of the recipe
         */
        var ingredientsText:String,

        /**
         * Preparationdescription of the recipe
         */
        var preparationDescription:String,

        /**
         * Picture of the Recipe
         */
        var picture:String,

        /**
         * Cookingtime of the recipe
         */
        var cookingTime:Int,

        /**
         * Preparationtime of the recipe
         */
        var preparationTime:Int,

        /**
         * Id of the user, which create the recipe
         */
        val userId:String?,

        /**
         * Date of the recipe, when it is created
         */
        var creationDate:Date,

        /**
         * Count of portions for people
         */
        var portions:Int,

        /**
         * The average rating of the recipe
         */
        var ratingAvg:Int,

        /**
         * Ingredientschapters of the recipe
         */
        var ingredientsChapter: List<IngredientChapterDto>?,

        /**
         * Tags of the recipes
         */
        var recipeTag:List<RecipeTagDto>?

)