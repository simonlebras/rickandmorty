package app.rickandmorty.inject

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import app.rickandmorty.core.injectannotations.ActivityContext
import app.rickandmorty.core.injectannotations.ActivityScope
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesSubcomponent
import software.amazon.lastmile.kotlin.inject.anvil.ForScope
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesSubcomponent(ActivityScope::class)
@SingleIn(ActivityScope::class)
interface ActivityComponent {
    @ForScope(AppScope::class)
    val viewModelFactory: ViewModelProvider.Factory

    val ComponentActivity.bind: @ActivityContext Context
        @Provides get() = this

    @ContributesSubcomponent.Factory(AppScope::class)
    interface Factory {
        fun createComponent(
            activity: ComponentActivity,
        ): ActivityComponent
    }

    companion object {
        fun create(activity: ComponentActivity): ActivityComponent = (activity.application as ActivityComponentFactoryOwner)
            .activityComponentFactory()
            .createComponent(activity)
    }
}
