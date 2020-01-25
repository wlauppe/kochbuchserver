package de.psekochbuch.exzellenzkoch.application.api

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("api/images")
interface FileApi {
    @GetMapping("/{imageName}")
    @ResponseBody
    fun getImage(@PathVariable imageName:String) : ResponseEntity<InputStreamResource>

    @PostMapping("")
    fun addImage(@RequestParam("file") file: MultipartFile) :String?
}