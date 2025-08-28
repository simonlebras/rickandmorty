plugins {
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)

  alias(libs.plugins.apollo)
}

apollo {
  service("rickandmorty") {
    packageName = "app.rickandmorty.data.graphql.schema"
    @Suppress("OPT_IN_USAGE")
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
