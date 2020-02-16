package de.psekochbuch.exzellenzkoch.application.service

import com.google.firebase.auth.FirebaseAuth
import de.psekochbuch.exzellenzkoch.application.dto.FileDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import de.psekochbuch.exzellenzkoch.security.firebase.FirebaseAuthentication
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File

/**
 * Class for management of files
 */
@Service
@Transactional
class FileService {

    @Autowired
    private val recipeDao:PublicRecipeDao? = null

    /**
     * Loads an image from the Disk and transforms it in a responseentity
     * @param imageName Name of the Image
     * @param userId Id of the user, who upload the image
     * @return The transformed image
     */
    fun getImage(imageName: String, userId:String) : ResponseEntity<InputStreamResource>
    {
        val file = File("images/$userId/$imageName")
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(InputStreamResource(file.inputStream()))
    }

    /**
     * Create folderlocation for the image. Then it creates the file. After that it creates the URL
     * which the image can load and at it to the recipe
     * @param file The file to save
     * @return The URL from the image
     */
    fun addImage(file: MultipartFile) :FileDto?
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        val userId = FirebaseAuth.getInstance().getUser(auth.principal as String).displayName
        val fileName = file.originalFilename

        val name = FilenameUtils.removeExtension(file.originalFilename)
        val folderPath = "images/$userId"
        val folderFile = File(folderPath)
        if(!folderFile.exists()) folderFile.mkdir()

        val localPath = "$folderPath/$fileName"

        val localFile = File(localPath)
        if(!localFile.exists())
        {
            localFile.writeBytes(file.bytes)
            return FileDto("api/$localPath")
        }
        var count = 1
        var editFileName = "images/"+ userId + "/$name" + "_$count" + "." + FilenameUtils.getExtension(fileName)
        while(File(editFileName).exists())
        {
            count++
            editFileName = "images/"+ userId + "/$name" + "_$count" + "." + FilenameUtils.getExtension(fileName)
        }
        File(editFileName).writeBytes(file.bytes)

        return FileDto("api/$editFileName")
    }

    /**
     * Update the image of the recipe
     * @param file The new image
     * @param recipeId The id of the recipe
     * @return The URL of the image
     */
    fun updateImage(file: MultipartFile, imageName: String, userId: String) :FileDto?
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == userId) {

            val localPath ="images/$userId/$imageName"

            val localFile = File(localPath)

            if (localFile.exists()) localFile.delete()

            localFile.writeBytes(file.bytes)
            return FileDto("api/$localPath")
        }
        return null
    }

    /**
     * Delete an image of a recipe
     * @param recipeId The id of the recipe
     * @return Empty path
     */
    fun deleteImage(imageName: String, userId: String) : FileDto?
    {
        val auth : FirebaseAuthentication = SecurityContextHolder.getContext().authentication as FirebaseAuthentication
        if(FirebaseAuth.getInstance().getUser(auth.principal as String).displayName == userId) {

            val localFile = File("images/$userId/$imageName")
            if (localFile.exists()) localFile.delete()

            return FileDto("")
        }
        return null
    }

    /**
     * Convert the Public-URL in a local path
     * @param imageUrl The of the image
     * @return The converted path
     */
    private fun getLocalPathFromImage(imageUrl: String): String {
        return imageUrl.substring(0, "api/".length)
    }

}