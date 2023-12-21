package app.rickandmorty.core.contentview

import android.app.Activity
import android.view.View

public fun interface ContentViewSetter {
    public fun setContentView(activity: Activity, view: View)
}
