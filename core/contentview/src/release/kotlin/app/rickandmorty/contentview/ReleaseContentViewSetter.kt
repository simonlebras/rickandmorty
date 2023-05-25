package app.rickandmorty.contentview

import android.app.Activity
import android.view.View
import javax.inject.Inject
import se.ansman.dagger.auto.AutoBind

@AutoBind
internal class ReleaseContentViewSetter @Inject constructor() : ContentViewSetter {
    override fun setContentView(activity: Activity, view: View) {
        activity.setContentView(view)
    }
}
