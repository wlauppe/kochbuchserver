import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.2.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
	kotlin("plugin.jpa") version "1.3.61"
}

group = "de.psekochbuch"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

buildscript {
	repositories {
		mavenCentral()

	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:2.2.2.RELEASE")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("mysql:mysql-connector-java")
	//testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")

	//JUnit Test
	testImplementation ("org.junit.jupiter:junit-jupiter-api:5.5.1")
	testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.5.1")

	//Firebase
	implementation ("com.google.firebase:firebase-admin:6.11.0")

	//MariaDb
	implementation ("org.mariadb.jdbc:mariadb-java-client:2.5.2")

	//Commons-IO for File-Handling
	implementation("commons-io:commons-io:2.6")

}

springBoot {
	mainClassName = "de.psekochbuch.exzellenzkoch.ExzellenzkochApplicationKt"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
