package com.example.mymusic.customview.layoutmanager

import androidx.annotation.FloatRange
import androidx.annotation.IntRange




class LayoutConfig {

    @IntRange(from = 2)
    var space = 60
    var maxStackCount = 3
    var initialStackCount = 0

    @FloatRange(from = 0.0, to = 1.0)
    var secondaryScale = 0f

    @FloatRange(from = 0.0, to = 1.0)
    var scaleRatio = 0f

    /**
     * the real scroll distance might meet requirement,
     * so we multiply a factor fro parallex
     */
    @FloatRange(from = 1.0, to = 2.0)
    var parallex = 1f
    var align: Align? = null
}