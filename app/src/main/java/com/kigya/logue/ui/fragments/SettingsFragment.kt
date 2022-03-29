package com.kigya.logue.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.kigya.logue.MainActivity
import com.kigya.logue.R
import com.kigya.logue.activities.RegisterActivity
import com.kigya.logue.utils.AUTH
import com.kigya.logue.utils.replaceActivity
import com.kigya.logue.utils.replaceFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
        }
        return true
    }
}