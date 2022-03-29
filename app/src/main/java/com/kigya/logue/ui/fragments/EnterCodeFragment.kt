package com.kigya.logue.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kigya.logue.R
import com.kigya.logue.databinding.FragmentEnterCodeBinding


class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    private var _binding: FragmentEnterCodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentEnterCodeBinding.inflate(inflater, container, false).also { _binding = it }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.registerInputCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val string = binding.registerInputCode.text.toString()
                if (string.length == 6) {
                    verifyCode()
                }
            }

        })
    }

    fun verifyCode() {
        Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}