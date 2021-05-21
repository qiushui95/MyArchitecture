import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

group = rootProject.extra["groupId"].cast<String>()
setProperty("archivesBaseName", "architecture-domain")
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

    if (rootProject.extra["isRemote"] == true) {
        api(rootProject.extra["dependencyFormat"].cast<String>().format("entity"))
    } else {
        api(project(":architecture_entity"))
    }

    compileOnly(rootProject.extra["coroutinesCore"].cast<String>())
}