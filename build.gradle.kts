// Top-level build file where you can add configuration options common to all sub-projects/modules.

val isRemote by extra(false)
val libVersion by extra("1.0.4")

val dependencyFormat by extra("com.github.qiushui95.MyArchitecture:%s:$libVersion")

val koinDependencyFormat = "io.insert-koin:%s:3.0.1"
val koinAndroidExt by extra(koinDependencyFormat.format("koin-android-ext"))

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}