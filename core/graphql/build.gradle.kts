plugins {
    id("app.rickandmorty.android-library")
    id("app.rickandmorty.hilt")
    alias(libs.plugins.apollo)
}

android {
    namespace = "app.rickandmorty.graphql"
}

androidComponents {
    beforeVariants(selector().withBuildType("debug")) { builder ->
        builder.enable = false
    }
}

apollo {
    service("rickandmorty") {
        packageName.set("app.rickandmorty.graphql")
        generateDataBuilders.set(true)
        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/app/rickandmorty/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.apollo.normalizedCache.sqlite)
    implementation(libs.apollo.runtime)

    implementation(projects.core.coroutines)
}
