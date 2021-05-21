import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

group = rootProject.extra["groupId"].cast<String>()
setProperty("archivesBaseName", "architecture-entity")
version = rootProject.extra["libVersion"].cast<String>()

tasks.register("sourcesJar", Jar::class) {
    dependsOn("classes")
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts.archives(tasks.getByName("sourcesJar"))

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
}