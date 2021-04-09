/*
 * Created by Evgeniya Zemlyanaya (@zzemlyanaya), ZZen Studio
 *  Copyright (c) 2021 . All rights reserved.
 */

package ru.zzenstudio.zenlaunch.main

import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.zzenstudio.zenlaunch.PrefsConst
import ru.zzenstudio.zenlaunch.RESULT
import ru.zzenstudio.zenlaunch.menu.AppInfo
import ru.zzenstudio.zenlaunch.R
import ru.zzenstudio.zenlaunch.databinding.FragmentMainBinding
import kotlin.math.abs


class MainFragment : Fragment() {

    private lateinit var mGestureDetector: GestureDetector
    private lateinit var binding: FragmentMainBinding

    private lateinit var customApps: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            customApps = it.getStringArrayList(PrefsConst.CUSTOM_APPS) as ArrayList<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        initGestureDetector()
        binding.root.setOnTouchListener { view, motionEvent ->
            view.performClick()
            mGestureDetector.onTouchEvent(motionEvent)
        }

        setCustomAppsNames()
        setCustomAppsClick()


        return binding.root
    }

    private fun setCustomAppsNames(){
        binding.app1Name.text = customApps[0]
        binding.app2Name.text = customApps[1]
        binding.app3Name.text = customApps[2]
        binding.app4Name.text = customApps[3]
    }

    private fun initGestureDetector() {
        mGestureDetector = GestureDetector(
            requireContext(),
            object : SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent?) {
                    (requireActivity() as MainActivity).showSettingsFragment()
                    return super.onLongPress(e)
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    (requireActivity() as MainActivity).updateNightMode()
                    return super.onDoubleTap(e)
                }

                override fun onDown(e: MotionEvent?): Boolean {
                    return true
                }

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    if (e1 == null || e2 == null)
                        return super.onFling(e1, e2, velocityX, velocityY)

                    if (e1.x - e2.x > 200 && abs(velocityX) > 200) {
                        // Fling to left
                        (requireActivity() as MainActivity).showRTLApp()

                    } else if (e2.x - e1.x > 200 && abs(velocityX) > 200) {
                        // Fling to right
                        (requireActivity() as MainActivity).showLTRApp()

                    } else if (e2.y - e1.y > 100 && abs(velocityY) > 100){
                        // Fling down
                        // TODO("handle down gesture")

                    } else if (e1.y - e2.y > 150 && abs(velocityY) > 100){
                        // Fling up
                        (requireActivity() as MainActivity).showMenuFragment(null)
                    }

                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            }
        )
    }

    private fun setCustomAppsClick() {
        binding.app1Name.setOnClickListener { (requireActivity() as MainActivity).showCustomApp(0) }
        binding.app2Name.setOnClickListener { (requireActivity() as MainActivity).showCustomApp(1) }
        binding.app3Name.setOnClickListener { (requireActivity() as MainActivity).showCustomApp(2) }
        binding.app4Name.setOnClickListener { (requireActivity() as MainActivity).showCustomApp(3) }

        binding.app1Name.setOnLongClickListener {
            setResultListener(PrefsConst.CUSTOM_APPS, 0)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.CUSTOM_APPS)
            true
        }
        binding.app2Name.setOnLongClickListener {
            setResultListener(PrefsConst.CUSTOM_APPS, 1)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.CUSTOM_APPS)
            true
        }
        binding.app3Name.setOnLongClickListener {
            setResultListener(PrefsConst.CUSTOM_APPS, 2)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.CUSTOM_APPS)
            true
        }
        binding.app4Name.setOnLongClickListener {
            setResultListener(PrefsConst.CUSTOM_APPS, 3)
            (requireActivity() as MainActivity).showMenuFragment(PrefsConst.CUSTOM_APPS)
            true
        }
    }

    private fun setResultListener(pref: String, id: Int) {
        parentFragmentManager.setFragmentResultListener(
            pref,
            this,
            { requestKey: String, result: Bundle ->
                val res = result.get(RESULT)!! as AppInfo
                (requireActivity() as MainActivity).updateApps(requestKey, res, id)
                customApps[id] = res.label
                setCustomAppsNames()
            }
        )
    }

    companion object {

        @JvmStatic
        fun newInstance(customAppsNames: ArrayList<String>) =
                MainFragment().apply {
                    arguments = Bundle().apply {
                        putStringArrayList(PrefsConst.CUSTOM_APPS, customAppsNames)
                    }
                }
    }
}