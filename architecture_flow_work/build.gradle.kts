import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "architecture-flow-work")

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        sourceSets.getAt("main").java.srcDirs("src/main/kotlin")
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
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("error"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("domain"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("starter"))

    } else {
        implementation(project(":architecture_constant"))
        implementation(project(":architecture_entity"))
        implementation(project(":architecture_error"))
        implementation(project(":architecture_domain"))
        implementation(project(":architecture_starter"))
    }

    implementation(rootProject.extra["annotation"].cast<String>())

    compileOnly(rootProject.extra["coroutinesCore"].cast<String>())

    compileOnly(rootProject.extra["koinAndroidExt"].cast<String>())

    compileOnly(rootProject.extra["lifecycleViewModel"].cast<String>())
}