import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven {
            name = "Central Portal Snapshots"
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")

            // Only search this repository for the specific dependency
            content {
                includeModule("com.freshworks", "dagger-workmanager")
                includeModule("com.freshworks", "logging")
                includeModule("com.freshworks.sdk", "freshdesk")
            }
        }
        mavenCentral()
    }
}
rootProject.name = "Southwest"
include(":app")
