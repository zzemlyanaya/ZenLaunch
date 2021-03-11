/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 11.03.2021, 16:11
 */

package ru.zzemlyanaya.zenlaunch.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import ru.zzemlyanaya.zenlaunch.*
import ru.zzemlyanaya.zenlaunch.App.Companion.prefs
import ru.zzemlyanaya.zenlaunch.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.editLtrApp.setOnClickListener {
            setResultListener(PrefsConst.LTR_APP)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.LTR_APP)
        }
        binding.editRtlApp.setOnClickListener {
            setResultListener(PrefsConst.RTL_APP)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.RTL_APP)
        }

        return binding.root
    }

    private fun setResultListener(pref: String) {
        parentFragmentManager.setFragmentResultListener(
            pref,
            this,
            { requestKey: String, result: Bundle ->
                (requireActivity() as MainActivity).updateApps(requestKey, result.get(RESULT)!!, null)
            }
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}