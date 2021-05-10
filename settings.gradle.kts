dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "MyArchitecture"

include(":app")
include(":lib_usecase")
include(":lib_usecase_impl")
