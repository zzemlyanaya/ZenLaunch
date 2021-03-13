/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 13.03.2021, 9:35
 */

package ru.zzemlyanaya.zenlaunch.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.zzemlyanaya.zenlaunch.*
import ru.zzemlyanaya.zenlaunch.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {
    private val appList = ArrayList<AppInfo>()
    private lateinit var binding: FragmentMenuBinding

    private var requestKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            requestKey = it.getString(REQUEST_KEY)
        }

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
            adapter =  if(requestKey == null)
                AppsRecyclerViewAdapter(
                { openApp(it) },
                { openAppDialog(it) },
                appList.toList()
            )
            else
                AppsRecyclerViewAdapter( {sendResult(it)}, null, appList.toList() )

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if(!recyclerView.canScrollVertically(-1))
                        openKeyboard()
                    else
                        closeKeyboard()
                    super.onScrolled(recyclerView, dx, dy)
                }

            })
        }

        openKeyboard()

        binding.searchApp.afterTextChanged {
            (binding.appsRecyclerView.adapter as AppsRecyclerViewAdapter).filter(it)
        }

        return binding.root
    }

    fun openKeyboard(){
        if (binding.searchApp.requestFocus())
            requireContext().showKeyboard(binding.searchApp)
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
        parentFragmentManager.popBackStack()
    }

    fun openAppDialog(app: AppInfo): Boolean{

        return true
    }

    private fun sendResult(app: AppInfo) {
        parentFragmentManager.setFragmentResult(
            requestKey!!,
            bundleOf(RESULT to app)
        )
        parentFragmentManager.popBackStack()
    }

    companion object {

        @JvmStatic
        fun newInstance(requestKey: String?) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(REQUEST_KEY, requestKey)
                }
            }
    }
}