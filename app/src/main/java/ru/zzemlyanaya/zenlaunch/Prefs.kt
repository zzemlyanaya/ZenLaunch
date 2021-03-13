/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.03.2021, 15:12
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
    const val IS_NIGHT_MODE = "is night mode"
}

val list = arrayListOf(AppInfo(), AppInfo(), AppInfo(), AppInfo())

class Prefs(prefs: KryptoPrefs): KryptoContext(prefs) {
    val customApps = json(PrefsConst.CUSTOM_APPS, list,true)
    val ltrApp = json(PrefsConst.LTR_APP, AppInfo(),true)
    val rtlApp = json(PrefsConst.RTL_APP, AppInfo(),true)
    val accent = int(PrefsConst.ACCENT, Accents.MINT.id, true)
    val fontSize = string(PrefsConst.FONT_SIZE, FontSizes.SMALL.name, true)
    val isNightMode = boolean(PrefsConst.IS_NIGHT_MODE, false, true)

    fun setPref(key: String, value: Any){
        when(key){
            PrefsConst.CUSTOM_APPS -> customApps.put(value as ArrayList<AppInfo>)
            PrefsConst.LTR_APP -> ltrApp.put(value as AppInfo)
            PrefsConst.RTL_APP -> rtlApp.put(value as AppInfo)
            PrefsConst.ACCENT -> accent.put(value as Int)
            PrefsConst.FONT_SIZE -> fontSize.put(value as String)
            PrefsConst.IS_NIGHT_MODE -> isNightMode.put(value as Boolean)
            else -> throw Exception("Unknown pref!")
        }
    }
}