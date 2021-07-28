/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya), ZZen Studio
 *  Copyright (c) 2021 . All rights reserved.
 */

package ru.zzenstudio.zenlaunch.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil
import ru.zzenstudio.zenlaunch.App.Companion.prefs
import ru.zzenstudio.zenlaunch.PrefsConst
import ru.zzenstudio.zenlaunch.R
import ru.zzenstudio.zenlaunch.databinding.ActivityMainBinding
import ru.zzenstudio.zenlaunch.menu.AppInfo
import ru.zzenstudio.zenlaunch.menu.MenuFragment
import ru.zzenstudio.zenlaunch.settings.AboutFragment
import ru.zzenstudio.zenlaunch.settings.SettingsFragment
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


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

        with(binding.textDate) {
            text = date
            setOnClickListener {
                val calendarUri: Uri = CalendarContract.CONTENT_URI
                    .buildUpon()
                    .appendPath("time")
                    .build()
                startActivity(Intent(Intent.ACTION_VIEW, calendarUri).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK })
            }
        }
        with(binding.textTime) {
            text = time
            setOnClickListener {
                startActivity(packageManager.getLaunchIntentForPackage("com.android.deskclock"))
            }
        }

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
            .replace(R.id.container,
                MainFragment.newInstance(customApps.map { it.label } as ArrayList<String>), "main")
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
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, AboutFragment(), "about")
            .commitAllowingStateLoss()
    }

    fun showRTLApp(){
        if (rtlApp.packageName != "") {
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(rtlApp.packageName)
            this@MainActivity.startActivity(launchIntent)
        }
        onBackPressed()
    }

    fun showLTRApp(){
        if (ltrApp.packageName != "") {
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(ltrApp.packageName)
            this@MainActivity.startActivity(launchIntent)
        }
        onBackPressed()
    }

    fun showCustomApp(id: Int) {
        if (customApps[id].packageName != ""){
            val launchIntent =
                this@MainActivity.packageManager.getLaunchIntentForPackage(customApps[id].packageName)
            this@MainActivity.startActivity(launchIntent)
        }
        onBackPressed()
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