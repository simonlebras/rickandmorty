plugins {
    id("app.rickandmorty.jvm-library")
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
    implementation(libs.apollo.runtime)
}
