package com.kigya.logue.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.PhoneAuthProvider
import com.kigya.logue.MainActivity
import com.kigya.logue.R
import com.kigya.logue.activities.RegisterActivity
import com.kigya.logue.databinding.FragmentEnterCodeBinding
import com.kigya.logue.utils.*


class EnterCodeFragment(private val phoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {

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
        (activity as RegisterActivity).title = phoneNumber
        binding.registerInputCode.addTextChangedListener(AppTextWatcher {
            val string = binding.registerInputCode.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = binding.registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid
                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener { secondTask ->
                        if (secondTask.isSuccessful) {
                            showToast("Welcome")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else {
                            showToast(secondTask.exception?.message.toString())
                        }
                    }

            } else {
                showToast(task.exception?.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}