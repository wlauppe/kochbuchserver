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
     * The URL ends with api/images
     * @param file Picture to store
     * @return The Online-Url of the File
     */
    @PostMapping("")
    @ResponseBody
    fun addImage(@RequestParam("file") file: MultipartFile) :FileDto?

    /**
     * PUT-Request to update an image
     * The URL ends with api/images
     * @param file Picture to update
     * @param imageName Name from the picture
     * @param userId The id of the user, which uploaded the picture
     * @return The Online-Url of the File
     */
    @PutMapping("/{userId}/{imageName}")
    @ResponseBody
    fun updateImage(@RequestParam("file") file: MultipartFile, @PathVariable imageName:String, @PathVariable userId:String) :FileDto?

    /**
     * DELETE-Request to delete an image from a recipe
     * The URL ends with api/images/userId/imageName
     * @param imageName Name form the picture
     * @param userId The id of the user, which uploaded the picture
     * @return Empty Url
     */
    @DeleteMapping("/{userId}/{imageName}")
    fun deleteImage(@PathVariable imageName:String, @PathVariable userId:String) : FileDto?
}