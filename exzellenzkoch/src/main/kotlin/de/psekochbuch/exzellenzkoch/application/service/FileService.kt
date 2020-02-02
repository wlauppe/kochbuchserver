package de.psekochbuch.exzellenzkoch.application.service

import de.psekochbuch.exzellenzkoch.application.dto.FileDto
import de.psekochbuch.exzellenzkoch.domain.model.PublicRecipe
import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
     * @param recipeId The id of the recipe, which should has the file
     * @return The URL from the image
     */
    fun addImage(file: MultipartFile, recipeId:Int) :FileDto?
    {
        val recipe : PublicRecipe = recipeDao?.findById(recipeId)?.get() ?: return null
        val fileName = file.originalFilename

        val name = FilenameUtils.removeExtension(file.originalFilename)
        val folderPath = "images/" + recipe.user?.userId
        val folderFile = File(folderPath)
        if(!folderFile.exists()) folderFile.mkdir()

        val localPath = "$folderPath/$fileName"

        val localFile = File(localPath)
        if(!localFile.exists())
        {
            localFile.writeBytes(file.bytes)
            recipe.picture = "api/$localPath"
            recipeDao.save(recipe)
            return FileDto("api/$localPath")
        }
        var count = 1
        var editFileName = "images/"+ recipe.user?.userId + "/$name" + "_$count" + FilenameUtils.getExtension(fileName)
        while(File(editFileName).exists())
        {
            count++
            editFileName = "images/"+ recipe.user?.userId + "/$name" + "_$count" + FilenameUtils.getExtension(fileName)
        }
        File(editFileName).writeBytes(file.bytes)
        recipe.picture = "api/$editFileName"
        recipeDao.save(recipe)
        return FileDto("api/$editFileName")
    }

    /**
     * Update the image of the recipe
     * @param file The new image
     * @param recipeId The id of the recipe
     * @return The URL of the image
     */
    fun updateImage(file: MultipartFile, recipeId:Int) :FileDto?
    {
        val recipe : PublicRecipe = recipeDao?.findById(recipeId)?.get() ?: return null

        val localPath = getLocalPathFromImage(recipe.picture)

        val localFile = File(localPath)

        if(localFile.exists()) localFile.delete()

        localFile.writeBytes(file.bytes)
        return FileDto("api/$localPath")
    }

    /**
     * Delete an image of a recipe
     * @param recipeId The id of the recipe
     * @return Empty path
     */
    fun deleteImage(recipeId: Int) : FileDto?
    {
        val recipe : PublicRecipe = recipeDao?.findById(recipeId)?.get() ?: return null

        val localFile = File(getLocalPathFromImage(recipe.picture))
        if(localFile.exists()) localFile.delete()

        recipe.picture = ""
        recipeDao.save(recipe)
        return FileDto("")
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