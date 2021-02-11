/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya) on $file.created
 * Copyright (c) 2021 . All rights reserved.
 * Last modified $file.lasModified
 */

package ru.zzemlyanaya.zenlaunch

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object Consts {
    const val PREFS_NAME = "ZenLaunchPrefs"
}

fun Fragment.hideKeyboard() {
    view?.let { requireActivity().hideKeyboard(it) }
}

fun Fragment.showKeyboard() {
    view?.let { requireActivity().showKeyboard(it) }
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}
fun Context.showKeyboard(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
}