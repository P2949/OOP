plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
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

    // Force JDK 21 for tests
    javaLauncher = javaToolchains.launcherFor { languageVersion.set(JavaLanguageVersion.of(21)) }
}

tasks.register<JavaExec>("run") {
    group = "application"
    description = "Run the test class"
    classpath = sourceSets["test"].runtimeClasspath + sourceSets["main"].runtimeClasspath
    mainClass.set("controllers.TestSave")
}