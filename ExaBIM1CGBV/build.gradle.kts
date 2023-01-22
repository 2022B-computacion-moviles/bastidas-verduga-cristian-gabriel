plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // Gson
    implementation("com.google.code.gson:gson:2.8.8")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}