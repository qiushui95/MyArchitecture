inline fun <reified T> getExtra(key: String): T {
    return rootProject.extra[key] as T
}

plugins {
    id("java-library")
    id("kotlin")
    id("com.github.dcendents.android-maven")
}

group = getExtra("groupId")
setProperty("archivesBaseName", getExtra<String>("archivesBaseNameFormat").format("ext"))
version = getExtra("libVersion")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(kotlin("reflect"))
}

tasks.register("sourcesJar", Jar::class) {
    dependsOn("classes")
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

artifacts.archives(tasks.getByName("sourcesJar"))