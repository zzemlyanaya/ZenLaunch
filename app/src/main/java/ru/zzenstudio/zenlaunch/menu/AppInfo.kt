/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya), ZZen Studio
 *  Copyright (c) 2021 . All rights reserved.
 */

package ru.zzenstudio.zenlaunch.menu

import java.io.Serializable

data class AppInfo(
    var label: String = "Your custom app",
    var packageName: String = ""
) : Serializable