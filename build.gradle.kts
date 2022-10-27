import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
    id("io.gitlab.arturbosch.detekt") version("1.22.0-RC2")
    id("org.sonarqube") version "3.4.0.2513"
}

group = "io.pipeliner"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.1.2")
    implementation("org.slf4j:slf4j-simple:2.0.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")

    implementation("io.insert-koin:koin-core:3.2.2")

    implementation("com.github.kittinunf.fuel:fuel:2.3.1")
    implementation("com.github.kittinunf.fuel:fuel-jackson:2.3.1")

    implementation("org.jetbrains.exposed:exposed-core:0.40.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.40.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.40.1")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.impossibl.pgjdbc-ng:pgjdbc-ng:0.8.9")

    implementation("org.flywaydb:flyway-core:9.6.0")

    testImplementation(kotlin("test"))
    testImplementation("com.tngtech.archunit:archunit-junit4:1.0.0")
    testImplementation("io.mockk:mockk:1.13.2")
}

kotlin.sourceSets["test"].kotlin.srcDirs("src/test", "src/archTest", "src/componentTest")

detekt {
    buildUponDefaultConfig = true
    allRules = true
    config = files("$projectDir/detekt/config.yml")
}

sonarqube {
    properties {
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "admin")
        property("sonar.password", "pipeliner")
        property("sonar.projectBaseDir", "${project.projectDir}/src")
        property("sonar.sources", "main")
        properties["sonar.tests"] = "test"
        properties["sonar.tests"] = "archTest"
        properties["sonar.tests"] = "componentTest"
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy("koverReport")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    finalizedBy("detekt")
}

application {
    mainClass.set("io.pipeliner.MainKt")
}
