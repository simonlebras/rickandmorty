plugins {
    alias(libs.plugins.rickandmorty.jvm.library)
    alias(libs.plugins.rickandmorty.kotlin.jvm)

    alias(libs.plugins.apollo)
}

apollo {
    service("rickandmorty") {
        packageName.set("app.rickandmorty.data.graphql.schema")
        generateDataBuilders.set(true)
        generateApolloMetadata = true
        schemaFiles.setFrom(
            "src/main/graphql/schema.graphqls",
            "src/main/graphql/extra.graphqls",
        )

        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }

        isADependencyOf(projects.data.character)
        isADependencyOf(projects.data.location)
    }
}

dependencies {
    implementation(libs.apollo.api)
}
