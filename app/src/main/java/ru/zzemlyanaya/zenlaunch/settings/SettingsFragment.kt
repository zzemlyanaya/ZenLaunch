/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya)
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 19.03.2021, 19:18
 */

package ru.zzemlyanaya.zenlaunch.settings

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.zzemlyanaya.zenlaunch.*
import ru.zzemlyanaya.zenlaunch.App.Companion.prefs
import ru.zzemlyanaya.zenlaunch.databinding.FragmentSettingsBinding
import ru.zzemlyanaya.zenlaunch.main.MainActivity

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private var accent = prefs.accent.get()
    private var fontSize = prefs.fontSize.get()

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

        binding.editFontSize.text = "${getString(R.string.edit_font_size)} small"

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

    private fun getFontSize(): String {
        return when(fontSize) {
            FontSizes.SMALL.name -> "20sp"
            FontSizes.MEDIUM.name -> "24sp"
            else -> "28sp"
        }
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