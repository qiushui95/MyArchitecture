// Top-level build file where you can add configuration options common to all sub-projects/modules.

val isRemote by extra(false)
val libVersion by extra("1.0.13")
val groupId by extra("com.github.qiushui95.MyArchitecture")

val dependencyFormat by extra("com.github.qiushui95.MyArchitecture:%s:$libVersion")

val koinDependencyFormat = "io.insert-koin:%s:3.0.2"
val koinAndroidExt by extra(koinDependencyFormat.format("koin-android-ext"))

val coroutinesDependencyFormat = "org.jetbrains.kotlinx:%s:1.5.0"
val coroutinesCore by extra(coroutinesDependencyFormat.format("kotlinx-coroutines-core"))

val annotation by extra("androidx.annotation:annotation:1.2.0")

val lifecycleDependencyFormat = "androidx.lifecycle:%s:2.3.1"
val lifecycleViewModel by extra(lifecycleDependencyFormat.format("lifecycle-viewmodel-ktx"))
val lifecycleRunTime by extra(lifecycleDependencyFormat.format("lifecycle-runtime-ktx"))

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}