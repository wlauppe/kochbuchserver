package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.PublicRecipeDto
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RequestMapping("/api/recipes")
interface PublicRecipeApi {

    @GetMapping("/{id}")
    fun getRecipe(@PathVariable(value = "id") id:Int) : PublicRecipeDto?

    @PostMapping ("")
    fun addRecipe(@Valid @RequestBody publicRecipe:PublicRecipeDto?)

    @PutMapping ("/{id}")
    fun updateRecipe(@Valid @RequestBody publicRecipe: PublicRecipeDto?, @PathVariable id:Int)

    @DeleteMapping ("/{id}")
    fun deleteRecipe(@PathVariable(value = "id") id:Int)

    @GetMapping("")
    fun search(@RequestParam("title") title:Optional<String>)

}