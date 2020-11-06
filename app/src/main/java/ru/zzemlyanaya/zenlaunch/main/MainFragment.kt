/*
 * *
 *  * Created by Eugeniya Zemlyanaya (@zzemlyanaya) on 06.11.20 12:03
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 06.11.20 12:03
 *
 */

package ru.zzemlyanaya.zenlaunch.main

import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.zzemlyanaya.zenlaunch.MainActivity
import ru.zzemlyanaya.zenlaunch.R
import ru.zzemlyanaya.zenlaunch.databinding.FragmentMainBinding
import kotlin.math.abs


class MainFragment : Fragment() {

    private lateinit var mGestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMainBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        initGestureDetector()
        binding.root.setOnTouchListener { view, motionEvent ->
            view.performClick()
            mGestureDetector.onTouchEvent(motionEvent)
        }

        return binding.root
    }

    private fun initGestureDetector() {
        mGestureDetector = GestureDetector(
            requireContext(),
            object : SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent?) {
                    (requireActivity() as MainActivity).showSettingsFragment()
                    return super.onLongPress(e)
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

                    if (e1.x - e2.x > 100 && abs(velocityX) > 200) {
                        // Fling left
                        (requireActivity() as MainActivity).showLeftApp()

                    } else if (e2.x - e1.x > 100 && abs(velocityX) > 200) {
                        // Fling right
                        (requireActivity() as MainActivity).showRightApp()

                    } else if (e2.y - e1.y > 100 && abs(velocityY) > 100){
                        // Fling down
                        // TODO("handle down gesture")

                    } else if (e1.y - e2.y > 100 && abs(velocityY) > 100){
                        // Fling up
                        (requireActivity() as MainActivity).showMenuFragment()
                    }

                    return super.onFling(e1, e2, velocityX, velocityY)
                }
            }
        )
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                MainFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}