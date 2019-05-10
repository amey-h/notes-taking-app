package org.ameyapps.notes.utils

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Typeface

class FontsManager {

    fun getRobotoLightFont(context: Context): Typeface {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/RobotoCondensedLight.ttf")
        return typeface
    }

    fun getRobotoRegularFont(context: Context): Typeface {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/RobotoCondensedRegular.ttf")
        return typeface
    }
}