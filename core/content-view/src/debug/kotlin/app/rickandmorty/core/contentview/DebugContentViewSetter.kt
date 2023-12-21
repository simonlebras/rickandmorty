package app.rickandmorty.core.contentview

import android.app.Activity
import android.view.View
import au.com.gridstone.debugdrawer.DebugDrawer
import au.com.gridstone.debugdrawer.DeviceInfoModule
import au.com.gridstone.debugdrawer.okhttplogs.HttpLogger
import au.com.gridstone.debugdrawer.okhttplogs.OkHttpLoggerModule
import au.com.gridstone.debugdrawer.timber.TimberModule
import javax.inject.Inject
import se.ansman.dagger.auto.AutoBind

@AutoBind
internal class DebugContentViewSetter @Inject constructor(
    private val httpLogger: HttpLogger,
) : ContentViewSetter {
    override fun setContentView(activity: Activity, view: View) {
        DebugDrawer.with(activity)
            .addSectionTitle(activity.getString(R.string.debugdrawer_logs_title))
            .addModule(OkHttpLoggerModule(httpLogger))
            .addModule(TimberModule())
            .addSectionTitle(activity.getString(R.string.debugdrawer_device_info_title))
            .addModule(DeviceInfoModule())
            .buildMainContainer()
            .apply { addView(view) }
    }
}
