import son.ysy.useful.dependencies.BuildVersion

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "starter")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

}