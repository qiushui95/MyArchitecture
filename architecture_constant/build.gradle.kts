plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "architecture-constant")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
}