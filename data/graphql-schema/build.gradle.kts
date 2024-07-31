plugins {
    alias(libs.plugins.rickandmorty.apollo)
    alias(libs.plugins.rickandmorty.jvm.library)
    alias(libs.plugins.rickandmorty.kotlin.jvm)
}

apollo {
    service("rickandmorty") {
        packageName = "app.rickandmorty.data.graphql.schema"
        generateDataBuilders = true
        generateApolloMetadata = true
        schemaFiles.setFrom(
            "src/main/graphql/schema.graphqls",
            "src/main/graphql/extra.graphqls",
        )

        introspection {
            endpointUrl = "https://rickandmortyapi.com/graphql"
            schemaFile = file("src/main/graphql/schema.graphqls")
        }
    }
}

dependencies {
    implementation(libs.apollo.api)
}
