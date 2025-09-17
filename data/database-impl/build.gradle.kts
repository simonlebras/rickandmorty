plugins {
  alias(libs.plugins.rickandmorty.android.multiplatformlibrary)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.room)

  alias(libs.plugins.metro)
}

kotlin {
  androidLibrary {
    namespace = "app.rickandmorty.data.database"
    //  defaultConfig { consumerProguardFiles("consumer-rules.pro") }

    lint {
      // TODO Remove RestrictedApi disabled rule
      disable.add("RestrictedApi")
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        api(project(":data:database-api"))

        implementation(project(":core:coroutines"))
        implementation(project(":core:metro"))

        implementation(libs.androidx.room.paging)
      }
    }

    jvmMain { dependencies { implementation(libs.okio) } }
  }
}
