package com.kigya.logue.ui.fragments

import android.os.Bundle
import android.view.*
import com.kigya.logue.MainActivity
import com.kigya.logue.R
import com.kigya.logue.databinding.FragmentChangeUsernameBinding
import com.kigya.logue.utils.*

class ChangeUsernameFragment : BaseFragment(R.layout.fragment_change_username) {

    private var _binding: FragmentChangeUsernameBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNewUsername: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentChangeUsernameBinding.inflate(inflater, container, false)
            .also { _binding = it }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        binding.settingsInputUsername.setText(USER.username)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> change()
        }
        return true
    }

    private fun change() {
        mNewUsername = binding.settingsInputUsername.text.toString().lowercase()
        if (mNewUsername.isEmpty()) {
            showToast(getString(R.string.field_is_empty))
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast(getString(R.string.user_already_exist))
                        deleteOldUsername()
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_updated))
                    parentFragmentManager.popBackStack()
                    USER.username = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(USER.username)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_updated))
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}