import son.ysy.useful.dependencies.BuildVersion
import son.ysy.useful.dependencies.AndroidDependency

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "error")

android {
    compileSdkVersion(BuildVersion.CompileSdk.value)
    buildToolsVersion = BuildVersion.BuildTools.value

    defaultConfig {
        minSdkVersion(BuildVersion.MinSdk.value)
        targetSdkVersion(BuildVersion.TargetSdk.value)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        sourceSets.getAt("main").java.srcDirs("src/main/kotlin")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    if (rootProject.extra["isRemote"] == true) {
        implementation("com.github.qiushui95.MyArchitecture:starter:${rootProject.extra["libVersion"]}")
    } else {
        implementation(project(":lib_constant"))
        implementation(project(":lib_starter"))
    }

    compileOnly(AndroidDependency.Koin.AndroidExt.fullGradle)

}