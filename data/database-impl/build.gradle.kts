plugins {
  alias(libs.plugins.rickandmorty.android.library)
  alias(libs.plugins.rickandmorty.codehealth)
  alias(libs.plugins.rickandmorty.kotlin.multiplatform)
  alias(libs.plugins.rickandmorty.room)

  alias(libs.plugins.metro)
}

android {
  namespace = "app.rickandmorty.data.database"

  defaultConfig { consumerProguardFiles("consumer-rules.pro") }
}

kotlin {
  androidTarget()

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
