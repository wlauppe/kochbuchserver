package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RequestMapping("/api/recipes")
interface PublicRecipeApi {

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable(value = "id") id:Int) : PublicRecipeDto?

    @PostMapping
    fun addRecipe(@Valid @RequestBody publicRecipe:PublicRecipeDto)

}