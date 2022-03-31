package com.kigya.logue.ui.fragments

import android.view.View
import androidx.fragment.app.Fragment
import com.kigya.logue.MainActivity

open class BaseFragment(layout: Int) : Fragment(layout) {

    private lateinit var mRootView: View

    override fun onStart() {
        super.onStart()
        if (activity is MainActivity) {
            (activity as MainActivity).mAppDrawer.disableDrawer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (activity is MainActivity) {
            (activity as MainActivity).mAppDrawer.enableDrawer()
        }
    }

}