/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.03.2021, 16:11
 */

package ru.zzemlyanaya.zenlaunch.menu

import java.io.Serializable

data class AppInfo(
    var label: String = "Your custom app",
    var packageName: String = ""
) : Serializable