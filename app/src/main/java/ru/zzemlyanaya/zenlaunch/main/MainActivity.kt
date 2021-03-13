/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.03.2021, 15:12
 */

package ru.zzemlyanaya.zenlaunch.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import ru.zzemlyanaya.zenlaunch.App.Companion.prefs
import ru.zzemlyanaya.zenlaunch.PrefsConst
import ru.zzemlyanaya.zenlaunch.R
import ru.zzemlyanaya.zenlaunch.databinding.ActivityMainBinding
import ru.zzemlyanaya.zenlaunch.menu.AppInfo
import ru.zzemlyanaya.zenlaunch.menu.MenuFragment
import ru.zzemlyanaya.zenlaunch.settings.SettingsFragment
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var customApps = prefs.customApps.get()
    private var ltrApp = prefs.ltrApp.get()
    private var rtlApp = prefs.rtlApp.get()
    private var isNightMode = prefs.isNightMode.get()

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        when(fragment!!.tag) {
            "settings", "menu" -> showMainFragment()
            "about" -> showSettingsFragment()
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       switchNightMode()

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val time: String = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(Calendar.getInstance().time)
        val date: String = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Calendar.getInstance().time)
        binding.textTime.text = time
        binding.textDate.text = date

        showMainFragment()
    }

    private fun switchNightMode(){
        if (isNightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun updateNightMode(){
        isNightMode = !isNightMode
        prefs.setPref(PrefsConst.IS_NIGHT_MODE, isNightMode)
        switchNightMode()
    }

    fun showMainFragment(){
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, MainFragment.newInstance(customApps.map { it.label } as ArrayList<String>), "main")
            .commitAllowingStateLoss()

        setDateTimeVisibility(true)
    }

    fun showMenuFragment(requestKey: String?){
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, MenuFragment.newInstance(requestKey), "menu")
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    fun showSettingsFragment(){
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, SettingsFragment.newInstance(), "settings")
            .commitAllowingStateLoss()

        setDateTimeVisibility(false)
    }

    fun showAboutFragment(){
//        supportFragmentManager.beginTransaction()
//            //.setTransition()
//            .replace(R.id.container, MainFragment.newInstance(), "about")
//            .commitAllowingStateLoss()
    }

    fun showRTLApp(){
        if (rtlApp.packageName != "") {
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(rtlApp.packageName)
            this@MainActivity.startActivity(launchIntent)
        }
    }

    fun showLTRApp(){
        if (ltrApp.packageName != "") {
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(ltrApp.packageName)
            this@MainActivity.startActivity(launchIntent)
        }
    }

    fun showCustomApp(id: Int) {
        if (customApps[id].packageName != ""){
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(customApps[id].packageName)
            this@MainActivity.startActivity(launchIntent)
        }
    }

    fun updateApps(whatToUpdate: String, value: Any, id: Int?) {
        if (id != null) {
            customApps[id] = value as AppInfo
            prefs.setPref(whatToUpdate, customApps)
        }
        else {
            prefs.setPref(whatToUpdate, value)
            when (whatToUpdate) {
                PrefsConst.LTR_APP -> ltrApp = value as AppInfo
                PrefsConst.RTL_APP -> rtlApp = value as AppInfo
            }
        }
    }

    private fun setDateTimeVisibility(isVisible: Boolean){
        binding.textDate.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        binding.textTime.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
    }
}