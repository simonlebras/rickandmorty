plugins {
    alias(libs.plugins.rickandmorty.jvm.library)
    alias(libs.plugins.rickandmorty.kotlin.jvm)

    alias(libs.plugins.apollo)
}

apollo {
    service("rickandmorty") {
        packageName.set("app.rickandmorty.graphql.schema")
        generateDataBuilders.set(true)
        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/app/rickandmorty/graphql/schema/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.apollo.api)
}
