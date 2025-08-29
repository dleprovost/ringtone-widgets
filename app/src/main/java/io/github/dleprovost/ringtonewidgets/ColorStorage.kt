package io.github.dleprovost.ringtonewidgets

import android.content.Context
import android.graphics.Color
import androidx.core.content.edit
import androidx.core.graphics.toColorInt

object ColorStorage {
    private const val PREFS = "colors_prefs"
    private const val KEY_COLOR_PREFIX = "color_"

    private val defaults = mapOf(
        "selected" to "#f8a35e".toColorInt(),
        "unselected" to "#c0c4ce".toColorInt()
    )

    fun saveColor(context: Context, id: String, color: Int) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit { putInt("$KEY_COLOR_PREFIX$id", color) }
    }

    fun getColor(context: Context, id: String): Int {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getInt("$KEY_COLOR_PREFIX$id", defaults.getOrDefault(id, Color.TRANSPARENT))
    }
}
