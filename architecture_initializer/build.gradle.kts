inline fun <reified T> getExtra(key: String): T {
    return rootProject.extra[key] as T
}

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

group = getExtra("groupId")
setProperty("archivesBaseName", getExtra<String>("archivesBaseNameFormat").format("initializer"))
version = getExtra("libVersion")

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

fun formatDependency(name: String): Any {
    return if (getExtra("isRemote")) {
        getExtra<String>("dependencyFormat").format(name)
    } else {
        project(getExtra<String>("dependencyLocalFormat").format(name))
    }
}

dependencies {
    implementation(formatDependency("constant"))
    implementation(formatDependency("entity"))
    implementation(formatDependency("model"))
    implementation(formatDependency("getter"))
    implementation(formatDependency("http"))
    implementation(formatDependency("error"))

    compileOnly(getExtra("koinAndroidExt"))
}