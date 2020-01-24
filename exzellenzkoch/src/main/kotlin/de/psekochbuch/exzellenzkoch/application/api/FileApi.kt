package de.psekochbuch.exzellenzkoch.application.api

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping("api/images")
interface FileApi {
    @GetMapping("/{imageName}")
    @ResponseBody
    fun getImage(@PathVariable imageName:String) : ResponseEntity<InputStreamResource>
}