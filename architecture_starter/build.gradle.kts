inline fun <reified T> getExtra(key: String): T {
    return rootProject.extra[key] as T
}

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

group = getExtra("groupId")
setProperty("archivesBaseName", getExtra<String>("archivesBaseNameFormat").format("starter"))
version = getExtra("libVersion")

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

tasks.register("androidSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

artifacts.archives(tasks.getByName("androidSourcesJar"))