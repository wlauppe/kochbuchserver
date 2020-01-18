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