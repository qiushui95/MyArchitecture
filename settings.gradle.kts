dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "MyArchitecture"

include(":app")
include(":lib_ext")
include(":lib_domain")
include(":lib_domain_impl")
include(":lib_starter")
include(":lib_error")
include(":lib_constant")
include(":lib_http")
include(":lib_model")