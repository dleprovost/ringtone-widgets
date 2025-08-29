package io.github.dleprovost.ringtonewidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.widget.RemoteViews

abstract class RingtoneWidgetProvider : AppWidgetProvider() {

    companion object {

        const val ACTION_SOUND = "io.github.dleprovost.ringtonewidgets.SET_SOUND"
        const val ACTION_VIBRATE = "io.github.dleprovost.ringtonewidgets.SET_VIBRATE"
        const val ACTION_SILENT = "io.github.dleprovost.ringtonewidgets.SET_SILENT"

        fun updateAllWidgets(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val idsHorizontal = appWidgetManager.getAppWidgetIds(
                ComponentName(context, RingtoneWidgetHorizontal::class.java)
            )
            val idsVertical = appWidgetManager.getAppWidgetIds(
                ComponentName(context, RingtoneWidgetVertical::class.java)
            )
            for (id in idsHorizontal + idsVertical) {
                val providerInfo = appWidgetManager.getAppWidgetInfo(id)
                val layoutId = providerInfo.initialLayout

                val views = RemoteViews(context.packageName, layoutId)

                colorize(context, views)
                appWidgetManager.updateAppWidget(id, views)

            }
        }

        fun colorize(context: Context, views: RemoteViews) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val selectedColor = ColorStorage.getColor(context, "selected")
            val unselectedColor = ColorStorage.getColor(context, "unselected")

            val selectedIndex = when (audioManager.ringerMode) {
                AudioManager.RINGER_MODE_NORMAL -> 0
                AudioManager.RINGER_MODE_VIBRATE -> 1
                AudioManager.RINGER_MODE_SILENT -> 2
                else -> -1
            }

            val icons = listOf(
                R.id.ringtonewidgeticon1,
                R.id.ringtonewidgeticon2,
                R.id.ringtonewidgeticon3
            )

            icons.forEachIndexed { index, iconId ->
                views.setInt(iconId, "setColorFilter", if (index == selectedIndex) selectedColor else unselectedColor)
            }
        }
    }

    abstract val layoutId: Int

    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) {
        for (id in ids) {
            updateWidget(context, manager, id)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        when (intent.action) {
            ACTION_SOUND -> audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
            ACTION_VIBRATE -> audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE
            ACTION_SILENT -> {
                if (PermissionHelper.hasDndPermission(context)) {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
                } else {
                    PermissionHelper.requestDndPermission(context)
                }
            }
        }
        val manager = AppWidgetManager.getInstance(context)
        val ids = manager.getAppWidgetIds(intent.component!!)
        for (id in ids) {
            updateWidget(context, manager, id)
        }
    }

    private fun updateWidget(context: Context, manager: AppWidgetManager, widgetId: Int) {
        val views = RemoteViews(context.packageName, layoutId)

        fun makePending(action: String): PendingIntent {
            val i = Intent(context, this::class.java).apply { this.action = action }
            return PendingIntent.getBroadcast(context, action.hashCode(), i, PendingIntent.FLAG_IMMUTABLE)
        }

        views.setOnClickPendingIntent(R.id.ringtonewidgeticon1, makePending(ACTION_SOUND))
        views.setOnClickPendingIntent(R.id.ringtonewidgeticon2, makePending(ACTION_VIBRATE))
        views.setOnClickPendingIntent(R.id.ringtonewidgeticon3, makePending(ACTION_SILENT))

        updateAllWidgets(context)

        manager.updateAppWidget(widgetId, views)
    }
}
