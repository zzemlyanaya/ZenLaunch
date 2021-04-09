/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya), ZZen Studio
 *  Copyright (c) 2021 . All rights reserved.
 */

package ru.zzenstudio.zenlaunch.settings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.zzenstudio.zenlaunch.*
import ru.zzenstudio.zenlaunch.App.Companion.prefs
import ru.zzenstudio.zenlaunch.databinding.FragmentSettingsBinding
import ru.zzenstudio.zenlaunch.main.MainActivity

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var accent = prefs.accent.get()

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

        val accentColour = getString(R.string.accent_colour)
        with(binding.editAccentColour) {
            setTextColor(Color.parseColor(accent))
            text = if (accent == Accents.MINT.id)
                "$accentColour ${getString(R.string.mint)}"
            else
                "$accentColour ${getString(R.string.red)}"
            setOnClickListener {
                if (accent == Accents.MINT.id) {
                    accent = Accents.FIRE_RED.id
                    this.text = "$accentColour ${getString(R.string.red)}"
                } else {
                    accent = Accents.MINT.id
                    this.text = "$accentColour ${getString(R.string.mint)}"
                }
                setTextColor(Color.parseColor(accent))
                binding.editAccentColour.setTextColor(Color.parseColor(accent))
                prefs.setPref(PrefsConst.ACCENT, accent)
            }
        }

       binding.about.setOnClickListener { (requireActivity() as MainActivity).showAboutFragment() }

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