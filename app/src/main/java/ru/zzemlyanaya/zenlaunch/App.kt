/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.03.2021, 16:11
 */

package ru.zzemlyanaya.zenlaunch

import android.app.Application
import com.kryptoprefs.preferences.KryptoBuilder


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        preferences = Prefs(KryptoBuilder.hybrid(this, PrefsConst.PREFS_NAME))
    }

    companion object {
        private lateinit var preferences : Prefs
        val prefs: Prefs
            get() = preferences

    }
}