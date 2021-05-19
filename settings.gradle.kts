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
include(":architecture_ext")
include(":architecture_domain")
include(":architecture_domain_impl")
include(":architecture_starter")
include(":architecture_error")
include(":architecture_constant")
include(":architecture_http")
include(":architecture_model")
include(":architecture_entity")
include(":architecture_flow_work")
include(":architecture_initializer")