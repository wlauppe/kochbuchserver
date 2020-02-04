package de.psekochbuch.exzellenzkoch

import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner


@TestPropertySource(locations = ["classpath:test.properties"])
//@SpringBootTest
class PublicRecipeTest {

    @Autowired
    var recipedao : PublicRecipeDao? = null

    @Test
    fun test()
    {
        val values = recipedao?.search("%Win%", null,null,null, 1, 10)
        val t = ""

    }
}