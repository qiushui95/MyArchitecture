plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "usecase")

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}