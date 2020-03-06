package de.psekochbuch.exzellenzkoch.application.dto

/**
 * Data transfer object of the Recipetag
 */
data class RecipeTagDto(
        /**
         * Name of the tag
         */
        var name:String
)
{
        override fun equals(other: Any?): Boolean {
                if(other is RecipeTagDto)
                {
                        if(other.name != name) return false
                        return true
                }
                return false
        }
}