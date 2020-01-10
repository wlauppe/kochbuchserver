package de.psekochbuch.exzellenzkoch.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import javax.annotation.PostConstruct

@Configuration
class FirebaseConfig {

    @Bean
    fun firebaseDatabse(): DatabaseReference {
        return FirebaseDatabase.getInstance().reference
    }

    @Value("\${de.psekochbuch.exzellenzkoch.firebase.database.url}")
    private val databaseUrl: String? = null

    @Value("\${de.psekochbuch.exzellenzkoch.firebase.config.path}")
    private val configPath: String? = null

    @PostConstruct
    fun init() {
        /**
         * https://firebase.google.com/docs/server/setup
         *
         * Create service account , download json
         */
        var file = File(configPath)
        val inputStream = file.inputStream()
        val google = GoogleCredentials.fromStream(inputStream)
        val options: FirebaseOptions = FirebaseOptions.Builder().setCredentials(google).setDatabaseUrl(databaseUrl).build()
        FirebaseApp.initializeApp(options)
    }
}