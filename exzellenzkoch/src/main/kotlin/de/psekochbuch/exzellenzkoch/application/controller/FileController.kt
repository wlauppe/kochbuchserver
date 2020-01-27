package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.FileApi
import de.psekochbuch.exzellenzkoch.application.dto.FileDto
import de.psekochbuch.exzellenzkoch.application.service.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Repository
class FileController : FileApi {

    @Autowired
    val service: FileService? = null

    override fun getImage(imageName: String, userId:String) :ResponseEntity<InputStreamResource>? {
        return service?.getImage(imageName, userId)
    }

    override fun addImage(file: MultipartFile, recipeId:Int) :FileDto? {
        return service?.addImage(file, recipeId)
    }

    override fun updateImage(file: MultipartFile, recipeId: Int): FileDto? {
        return service?.updateImage(file,recipeId)
    }

    override fun deleteRecipe(recipeId: Int): FileDto? {
        return service?.deleteImage(recipeId)
    }

}