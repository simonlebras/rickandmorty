plugins {
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.spotless)

  alias(libs.plugins.apollo)
  alias(libs.plugins.dependencyanalysis)
  alias(libs.plugins.sortdependencies)
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.graphql.schema"
    generateDataBuilders = true
    generateApolloMetadata = true
    schemaFiles.setFrom("src/main/graphql/schema.graphqls", "src/main/graphql/extra.graphqls")

    introspection {
      endpointUrl = "https://rickandmortyapi.com/graphql"
      schemaFile = file("src/main/graphql/schema.graphqls")
    }
  }
}

kotlin { sourceSets { commonMain { dependencies { implementation(libs.apollo.api) } } } }
