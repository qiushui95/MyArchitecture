import org.jetbrains.kotlin.utils.addToStdlib.cast

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "architecture-http")

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
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("ext"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("constant"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("starter"))
        implementation(rootProject.extra["dependencyFormat"].cast<String>().format("error"))
    } else {
        implementation(project(":architecture_ext"))
        implementation(project(":architecture_constant"))
        implementation(project(":architecture_starter"))
        implementation(project(":architecture_error"))
    }

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")

    val retrofitVersion = "2.9.0"

    api("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion"){
        exclude("com.squareup.moshi")
    }

    compileOnly("com.squareup.moshi:moshi:1.12.0")

    compileOnly(rootProject.extra["koinAndroidExt"].cast<String>())
}