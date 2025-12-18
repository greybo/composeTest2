package com.gurman.myapplication.utils

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat


fun getColorListSafe(context: Context, resId: Int): ColorStateList {
    val colorInt = getSafeColor(context, resId)
    return ColorStateList.valueOf(colorInt)
}

@Suppress("unused", "DEPRECATION")
fun getSafeColor(context: Context, id: Int): Int {
    return try {
        ContextCompat.getColor(context, id)
    } catch (e: Exception) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ResourcesCompat.getColor(context.resources, id, null)
        } else {
            context.resources.getColor(id)
        }
    }
}