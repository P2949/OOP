plugins {
    id("java")
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}
tasks.shadowJar {
    manifest {
        attributes(
            "Main-Class" to "app.Main.Class"
        )
    }

    // Shadow plugin automatically excludes signature files
    mergeServiceFiles()
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "app.Main.Class"
        )
    }

    // Create a fat JAR with dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    // Exclude signature files to prevent SecurityException
    exclude("META-INF/*.SF")
    exclude("META-INF/*.DSA")
    exclude("META-INF/*.RSA")
}

dependencies {
    implementation("com.opencsv:opencsv:5.12.0")
    implementation("org.jetbrains:annotations:26.0.2-1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")
}

tasks.test {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false

    javaLauncher = javaToolchains.launcherFor { languageVersion.set(JavaLanguageVersion.of(21)) }
}

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Run the test class"
    classpath = sourceSets["test"].runtimeClasspath + sourceSets["main"].runtimeClasspath
    mainClass.set("controllers.TestSave")
}