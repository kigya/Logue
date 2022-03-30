package com.kigya.logue.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.kigya.logue.MainActivity
import com.kigya.logue.R
import com.kigya.logue.databinding.FragmentChangeNameBinding
import com.kigya.logue.databinding.FragmentEnterPhoneBinding
import com.kigya.logue.utils.*


class ChangeNameFragment : Fragment(R.layout.fragment_change_name) {

    private var _binding: FragmentChangeNameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentChangeNameBinding.inflate(inflater, container, false).also { _binding = it }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() {
        val name = binding.settingsInputName.text.toString()
        val surname = binding.settingsInputSurname.text.toString()

        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_is_empty))
        } else {
            val fullName= "$name $surname".trim()
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullName).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_updated))
                        USER.fullname = fullName
                        parentFragmentManager.popBackStack()
                    }
                }
        }
    }
}