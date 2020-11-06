/*
 * *
 *  * Created by Eugeniya Zemlyanaya (@zzemlyanaya) on 06.11.20 12:03
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 06.11.20 12:03
 *
 */

package ru.zzemlyanaya.zenlaunch.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.zzemlyanaya.zenlaunch.R

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
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