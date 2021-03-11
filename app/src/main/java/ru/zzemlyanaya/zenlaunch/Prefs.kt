/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.03.2021, 16:11
 */

package ru.zzemlyanaya.zenlaunch

import com.kryptoprefs.context.KryptoContext
import com.kryptoprefs.gson.json
import com.kryptoprefs.preferences.KryptoPrefs
import ru.zzemlyanaya.zenlaunch.menu.AppInfo

object PrefsConst {
    const val PREFS_NAME = "ZenLaunchPrefs"
    const val CUSTOM_APPS = "custom apps"
    const val LTR_APP = "left to right app"
    const val RTL_APP = "right to left app"
    const val ACCENT = "accent"
    const val FONT_SIZE = "font size"
}

val list = arrayListOf(AppInfo(), AppInfo(), AppInfo(), AppInfo())

class Prefs(prefs: KryptoPrefs): KryptoContext(prefs) {
    val customApps = json(PrefsConst.CUSTOM_APPS, list,true)
    val ltrApp = json(PrefsConst.LTR_APP, AppInfo(),true)
    val rtlApp = json(PrefsConst.RTL_APP, AppInfo(),true)
    val accent = int(PrefsConst.ACCENT, Accents.MINT.id, true)
    val fontSize = string(PrefsConst.FONT_SIZE, FontSizes.SMALL.name, true)

    fun setPref(key: String, value: Any){
        when(key){
            PrefsConst.CUSTOM_APPS -> customApps.put(value as ArrayList<AppInfo>)
            PrefsConst.LTR_APP -> ltrApp.put(value as AppInfo)
            PrefsConst.RTL_APP -> rtlApp.put(value as AppInfo)
            PrefsConst.ACCENT -> accent.put(value as Int)
            PrefsConst.FONT_SIZE -> fontSize.put(value as String)
            else -> throw Exception("Unknown pref!")
        }
    }
}