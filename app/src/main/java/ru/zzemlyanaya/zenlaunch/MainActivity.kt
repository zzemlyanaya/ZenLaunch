/*
 * *
 *  * Created by Eugeniya Zemlyanaya (@zzemlyanaya) on 06.11.20 12:03
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 06.11.20 12:03
 *
 */

package ru.zzemlyanaya.zenlaunch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.zzemlyanaya.zenlaunch.databinding.ActivityMainBinding
import ru.zzemlyanaya.zenlaunch.main.MainFragment
import ru.zzemlyanaya.zenlaunch.menu.MenuFragment
import ru.zzemlyanaya.zenlaunch.settings.SettingsFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        when(fragment!!.tag) {
            "settings", "menu" -> showMainFragment()
            "about" -> showSettingsFragment()
            else -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        val time: String = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(Calendar.getInstance().time)
        val date: String = SimpleDateFormat("dd/MM/yy").format(Calendar.getInstance().time)
        binding.textTime.text = time
        binding.textDate.text = date

        showMainFragment()
    }

    fun showMainFragment(){
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, MainFragment.newInstance(), "main")
            .commitAllowingStateLoss()

        setDateTimeVisibility(true)
    }

    fun showMenuFragment(){
        supportFragmentManager.beginTransaction()
            //.setTransition()
            .replace(R.id.container, MenuFragment.newInstance(), "menu")
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
            .replace(R.id.container, MainFragment.newInstance(), "about")
            .commitAllowingStateLoss()
    }

    fun showLeftApp(){
        // TODO("handle if no app defined")
    }

    fun showRightApp(){
        // TODO("handle if no app defined")
    }

    fun setDateTimeVisibility(isVisible: Boolean){
        binding.textDate.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
        binding.textTime.visibility = if(isVisible) View.VISIBLE else View.INVISIBLE
    }
}