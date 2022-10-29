import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val javaVersion = JavaVersion.VERSION_18
val kotlinLanguageVersion = "1.7"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.sksamuel.hoplite:hoplite-core:2.6.5")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        @Suppress("SuspiciousCollectionReassignment")
        freeCompilerArgs += listOf("-Xjsr305=strict")
        jvmTarget = javaVersion.majorVersion
        languageVersion = kotlinLanguageVersion
        apiVersion = kotlinLanguageVersion
    }
}
