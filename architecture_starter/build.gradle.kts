import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

group = rootProject.extra["groupId"].cast<String>()
setProperty("archivesBaseName", "architecture-starter")
version = rootProject.extra["libVersion"].cast<String>()

tasks.register("androidSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

artifacts.archives(tasks.getByName("androidSourcesJar"))

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

}