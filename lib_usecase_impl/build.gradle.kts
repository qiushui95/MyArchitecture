plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

setProperty("archivesBaseName", "usecase_impl")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    if (rootProject.extra["isRemote"]==true){
        api("com.github.qiushui95.MyArchitecture:usecase:${rootProject.extra["libVersion"]}")
    }else{
        api(project(":lib_usecase"))
    }

    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-RC")
}