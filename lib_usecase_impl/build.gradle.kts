plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

group = "com.github.qiushui.architecture"
setProperty("archivesBaseName", "usecase_impl")

java {
    sourceCompatibility = JavaVersion.VERSION_15
    targetCompatibility = JavaVersion.VERSION_15
}

dependencies {
//    println(rootProject.extra["isRemote"])

    api(project(":lib_usecase"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}