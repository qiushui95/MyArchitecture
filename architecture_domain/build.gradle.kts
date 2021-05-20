import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

group = rootProject.extra["groupId"].cast<String>()
version = rootProject.extra["libVersion"].cast<String>()

setProperty("archivesBaseName", "architecture-domain")

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