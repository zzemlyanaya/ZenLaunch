/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.03.2021, 16:11
 */

package ru.zzemlyanaya.zenlaunch

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

enum class Accents(val id: Int) {
    MINT(0),
    FIRE_RED(1)
}

enum class FontSizes { SMALL, MEDIUM, LARGE }

const val RESULT = "result"
const val REQUEST_KEY = "request key"

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