import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

group = rootProject.extra["groupId"].cast<String>()
setProperty("archivesBaseName", "architecture-initializer")
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

    if (rootProject.extra["isRemote"] == true) {
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("constant"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("entity"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("model"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("getter"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("http"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("error"))
    } else {
        implementation(project(":architecture_constant"))
        implementation(project(":architecture_entity"))
        implementation(project(":architecture_model"))
        implementation(project(":architecture_getter"))
        implementation(project(":architecture_http"))
        implementation(project(":architecture_error"))
    }

    compileOnly(rootProject.extra["koinAndroidExt"].cast<String>())
}