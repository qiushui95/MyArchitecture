plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "constant")

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
}