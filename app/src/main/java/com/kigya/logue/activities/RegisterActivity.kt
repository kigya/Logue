package com.kigya.logue.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kigya.logue.R
import com.kigya.logue.databinding.ActivityRegisterBinding
import com.kigya.logue.ui.fragments.EnterPhoneFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        supportFragmentManager.beginTransaction().add(R.id.register_data_container, EnterPhoneFragment())
    }
}