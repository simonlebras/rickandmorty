package app.rickandmorty.gradle.util

import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.HasAndroidTestBuilder
import com.android.build.api.variant.VariantBuilder
import org.gradle.api.Project

context(Project)
internal fun <T> AndroidComponentsExtension<*, T, *>.disableEmptyAndroidTests()
    where T : VariantBuilder,
          T : HasAndroidTestBuilder {
    beforeVariants { builder ->
        builder.enableAndroidTest = builder.enableAndroidTest &&
            projectDir.resolve("src/androidTest").exists()
    }
}
