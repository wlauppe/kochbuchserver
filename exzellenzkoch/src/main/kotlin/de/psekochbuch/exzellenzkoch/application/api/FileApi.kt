package de.psekochbuch.exzellenzkoch.application.api

import de.psekochbuch.exzellenzkoch.application.dto.FileDto
import org.springframework.core.io.InputStreamResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Interface for the api from the picturesfiles
 */
@RequestMapping("api/images")
interface FileApi {

    /**
     * GET-Request to gets the file with the url
     * The URL ends with api/images/userid/imageName
     * @param imageName Name form the picture
     * @param userId The id of the user, which uploaded the picture
     * @return The picture
     */
    @GetMapping("/{userId}/{imageName}")
    @ResponseBody
    fun getImage(@PathVariable imageName:String, @PathVariable userId:String) : ResponseEntity<InputStreamResource>?

    /**
     * POST-Request to add an image to a recipe
     * The URL ends with api/images/recipeId
     * @param file Picture to store
     * @param recipeId The id of the recipe, which has the picture
     * @return The Online-Path of the File
     */
    @PostMapping("/{recipeId}")
    @ResponseBody
    fun addImage(@RequestParam("file") file: MultipartFile, @PathVariable recipeId:Int) :FileDto?

    /**
     * PUT-Request to update an image from a recipe
     * The URL ends with api/images/recipeId
     * @param file Picture to update
     * @param recipeId The id
     */
    @PutMapping("/{recipeId}")
    @ResponseBody
    fun updateImage(@RequestParam("file") file: MultipartFile, @PathVariable recipeId:Int) :FileDto?

    @DeleteMapping("/{recipeId}")
    fun deleteRecipe(@PathVariable  recipeId:Int) :FileDto?
}