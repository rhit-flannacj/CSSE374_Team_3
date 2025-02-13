// build.gradle.kts

plugins {
    // The Java plugin is `java` in Groovy DSL,
// but in Kotlin DSL, you write:
    java
    // For Shadow, you must also do:
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // In Kotlin DSL, you *must* call implementation(...) with parentheses
    implementation("org.ow2.asm:asm:9.5")
    // Add more dependencies as needed, like:
    // implementation("org.ow2.asm:asm-util:9.5")
    // implementation("org.ow2.asm:asm-tree:9.5")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.team3.Controller"
    }
}
