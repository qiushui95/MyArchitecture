inline fun <reified T> getExtra(key: String): T {
    return rootProject.extra[key] as T
}

plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

group = getExtra("groupId")
setProperty("archivesBaseName", getExtra<String>("archivesBaseNameFormat").format("http"))
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

fun formatDependency(name: String): Any {
    return if (getExtra("isRemote")) {
        getExtra<String>("dependencyFormat").format(name)
    } else {
        project(getExtra<String>("dependencyLocalFormat").format(name))
    }
}

dependencies {
    implementation(formatDependency("ext"))
    implementation(formatDependency("constant"))
    implementation(formatDependency("starter"))
    implementation(formatDependency("error"))

    implementation(kotlin("reflect"))

    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    val retrofitVersion = "2.9.0"

    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion") {
        exclude("com.squareup.moshi")
    }

    compileOnly("com.squareup.moshi:moshi:1.12.0")

    compileOnly(getExtra("koinAndroidExt"))
}

tasks.register("androidSourcesJar", Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets["main"].java.srcDirs)
}

artifacts.archives(tasks.getByName("androidSourcesJar"))