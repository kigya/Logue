package com.kigya.logue.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kigya.logue.MainActivity
import com.kigya.logue.R
import com.kigya.logue.activities.RegisterActivity
import com.kigya.logue.databinding.FragmentEnterPhoneBinding
import com.kigya.logue.utils.AUTH
import com.kigya.logue.utils.replaceActivity
import com.kigya.logue.utils.replaceFragment
import com.kigya.logue.utils.showToast
import java.util.concurrent.TimeUnit

class EnterPhoneFragment : Fragment(R.layout.fragment_enter_phone) {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!
    private lateinit var mPhoneNumber: String
    private lateinit var mCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks

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
        mCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showToast("Welcome")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else {
                        showToast(task.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, id))
            }
        }
        binding.registerBtnNext.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        if (binding.registerInputPhoneNumber.text.toString().isEmpty()) {
            showToast(getString(R.string.enter_phone_number))
        } else {
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = binding.registerInputPhoneNumber.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallback
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}