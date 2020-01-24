package de.psekochbuch.exzellenzkoch.application.controller

import de.psekochbuch.exzellenzkoch.application.api.FileApi
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository

@Repository
class FileController : FileApi {
    override fun getImage(imageName: String) :ResponseEntity<InputStreamResource> {
        val resource = ClassPathResource("images/Wolke2.jpg")
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(InputStreamResource(resource.inputStream))
    }
}