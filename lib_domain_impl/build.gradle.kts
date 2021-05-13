import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "domain-impl")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    if (rootProject.extra["isRemote"] == true) {
        api(rootProject.extra["dependencyFormat"].cast<String>().format("domain"))
        api(rootProject.extra["dependencyFormat"].cast<String>().format("entity"))
    } else {
        api(project(":lib_domain"))
        api(project(":lib_entity"))
    }

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}