package de.psekochbuch.exzellenzkoch

import de.psekochbuch.exzellenzkoch.infrastructure.dao.PublicRecipeDao
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
//import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.junit4.SpringRunner


//@TestPropertySource(locations = ["classpath:test.properties"])
@SpringBootTest
//@RunWith(SpringRunner::class)
//@DataJpaTest
class PublicRecipeTest {

    @Autowired
    var recipedao : PublicRecipeDao? = null

    @Test
    public fun test()
    {
        val values = recipedao?.search("%Win%", null,null,null, 1, 10)
        val t = ""
        Assertions.assertThat(values).isNotEmpty
    }
}