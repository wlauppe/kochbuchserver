package de.psekochbuch.exzellenzkoch.application.dto

/**
 * Data Transfer Object of the ingredients
 */
data class IngredientDto(
        /**
         * Id of the chapter of the ingredient
         */
        var chapterId:Int,

        /**
         * Name of the ingredient
         */
        var nameIngredient:String,

        /**
         * Amount of the ingredient
         */
        var amount:Double,

        /**
         * Unit of the ingredient
         */
        var unit:String
)
{
        override fun equals(other: Any?): Boolean {
                if(other is IngredientDto)
                {
                        if(other.chapterId != this.chapterId) return false
                        if(other.nameIngredient != this.nameIngredient) return false
                        if(other.amount != this.amount) return false
                        if(other.unit != this.unit) return false
                        return true
                }
                return false
        }

        override fun hashCode(): Int {
                var result = chapterId
                result = 31 * result + nameIngredient.hashCode()
                result = 31 * result + amount.hashCode()
                result = 31 * result + unit.hashCode()
                return result
        }
}