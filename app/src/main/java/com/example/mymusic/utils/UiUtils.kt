package com.example.mymusic.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


object UiUtils {

    fun getScreenWidthPixels(context: Context): Int {
        val dm = DisplayMetrics()
        (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            .getMetrics(dm)
        return dm.widthPixels
    }

    fun dipToPx(context: Context, dip: Int): Int {
        return (dip * getScreenDensity(context) + 0.5f).toInt()
    }

    private fun getScreenDensity(context: Context): Float {
        return try {
            val dm = DisplayMetrics()
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                .getMetrics(dm)
            dm.density
        } catch (e: Exception) {
            DisplayMetrics.DENSITY_DEFAULT.toFloat()
        }
    }
}