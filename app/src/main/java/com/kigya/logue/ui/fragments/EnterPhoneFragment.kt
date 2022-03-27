package com.kigya.logue.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kigya.logue.R
import com.kigya.logue.databinding.FragmentEnterPhoneBinding

class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentEnterPhoneBinding.inflate(inflater, container, false).also { _binding = it }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.registerBtnNext.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        if (binding.registerInputPhoneNumber.text.toString().isEmpty()) {
            Toast.makeText(activity, getString(R.string.enter_phone_number), Toast.LENGTH_SHORT)
                .show()
        } else {
            parentFragmentManager.beginTransaction()
                .replace(R.id.register_data_container, EnterCodeFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}