package de.psekochbuch.exzellenzkoch.application.dto

/**
 * Data Transfer Object of the ingredients chapters
 */
data class IngredientChapterDto(
        /**
         * Id of the chapter
         */
        var id:Int,

        /**
         * Name of the chapter
         */
        var name:String,

        /**
         * List of ingredients
         */
        var ingredient:List<IngredientDto>?
)
{
        override fun equals(other: Any?): Boolean {
                if(other is IngredientChapterDto)
                {
                        if(other.id != this.id) return false
                        if(other.name != this.name) return false
                        val ingr = other.ingredient
                        if(ingr != null)
                        {
                                if(!ingr.equals(this.ingredient)) return false
                        }
                        return true
                }
                return false
        }
}