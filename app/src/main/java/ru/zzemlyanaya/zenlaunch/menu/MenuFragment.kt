/*
 * *
 *  * Created by Eugeniya Zemlyanaya (@zzemlyanaya) on 06.11.20 12:03
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 06.11.20 12:03
 *
 */

package ru.zzemlyanaya.zenlaunch.menu

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.zzemlyanaya.zenlaunch.R
import ru.zzemlyanaya.zenlaunch.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private val appList = ArrayList<AppInfo>()

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMenuBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        with(binding.appsRecyclerView){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AppsRecyclerViewAdapter(
                { openApp(it) },
                { openAppDialog(it) },
                appList.toList()
            )
        }

        return binding.root
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