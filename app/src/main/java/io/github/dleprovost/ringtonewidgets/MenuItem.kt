package io.github.dleprovost.ringtonewidgets

import android.graphics.Color

data class MenuItem(
    val title: String,
    val isColor: Boolean,
    val colorId: String? = null,
    val color: Int = Color.TRANSPARENT
)
