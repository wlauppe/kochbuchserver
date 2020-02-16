package de.psekochbuch.exzellenzkoch.application.dto

import java.util.*

/**
 * Data Transfer Object of the public recipe
 */
data class PublicRecipeDto(
        /**
         * Id of the recipe
         */
        var id:Int,

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
        var creationDate:String,

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

){
        override fun equals(other: Any?): Boolean {
                if(other is PublicRecipeDto) {
                        val recipe: PublicRecipeDto = other
                        if(recipe.id != this.id) return false
                        if(recipe.title != this.title) return false
                        if(recipe.ingredientsText != this.ingredientsText) return false
                        if(recipe.preparationDescription != this.preparationDescription) return false
                        if(recipe.picture != this.picture) return false
                        if(recipe.cookingTime != this.cookingTime) return false
                        if(recipe.preparationTime != this.preparationTime) return false
                        if(recipe.userId != this.userId) return false
                        if(recipe.creationDate != this.creationDate) return false
                        if(recipe.portions != this.portions) return false
                        if(recipe.ratingAvg != this.ratingAvg) return false
                        val chapters = recipe.ingredientsChapter
                        if(chapters != null) {
                                if (!chapters.equals(this.ingredientsChapter)) return false
                        }
                        val tags = recipe.recipeTag
                        if(tags != null)
                        {
                                if(!tags.equals(this.recipeTag)) return false
                        }
                        return true
                } else
                {
                        return false
                }
        }
}