/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya) on $file.created
 * Copyright (c) 2021 . All rights reserved.
 * Last modified $file.lasModified
 */

package ru.zzemlyanaya.zenlaunch.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zzemlyanaya.zenlaunch.R
import ru.zzemlyanaya.zenlaunch.databinding.FragmentMenuBinding
import ru.zzemlyanaya.zenlaunch.hideKeyboard
import ru.zzemlyanaya.zenlaunch.showKeyboard


class MenuFragment : Fragment() {
    private val appList = ArrayList<AppInfo>()

    private lateinit var binding: FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val packageManager: PackageManager = requireContext().packageManager

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val allApps = packageManager.queryIntentActivities(i, 0)
        for (resolveInfo in allApps) {
            val app = AppInfo(
                resolveInfo.loadLabel(packageManager).toString(),
                resolveInfo.activityInfo.packageName
            )
            appList.add(app)
        }

        appList.sortWith { o1, o2 -> o1.label.compareTo(o2.label) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        with(binding.appsRecyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AppsRecyclerViewAdapter(
                { openApp(it) },
                { openAppDialog(it) },
                appList.toList()
            )
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(!recyclerView.canScrollVertically(-1))
                        openKeyboard()
                    super.onScrolled(recyclerView, dx, dy)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState != RecyclerView.SCROLL_STATE_IDLE)
                        closeKeyboard()
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

        openKeyboard()

        return binding.root
    }

    fun openKeyboard(){
        if (binding.searchApp.requestFocus()) {
            this.showKeyboard()
        }
    }

    fun closeKeyboard(){
        if (binding.searchApp.hasFocus()) {
            binding.searchApp.clearFocus()
            this.hideKeyboard()
        }
    }

    fun openApp(app: AppInfo){
        val launchIntent =
            requireContext().packageManager.getLaunchIntentForPackage(app.packageName)
        requireActivity().startActivity(launchIntent)
    }

    fun openAppDialog(app: AppInfo): Boolean{

        return true
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            MenuFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}