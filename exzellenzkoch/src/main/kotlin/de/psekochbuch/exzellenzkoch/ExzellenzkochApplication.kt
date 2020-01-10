package de.psekochbuch.exzellenzkoch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class ExzellenzkochApplication

fun main(args: Array<String>) {
	runApplication<ExzellenzkochApplication>(*args)
}
