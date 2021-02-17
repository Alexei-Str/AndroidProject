package com.app.myapplication.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.myapplication.AuthManager
import com.app.myapplication.R
import kotlinx.android.synthetic.main.fragment_login.*

class login : Fragment() {

    private val aM: AuthManager =
        AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        loginBtnAccept.setOnClickListener {

            if(TextUtils.isEmpty(etEmailL.text)){
                etEmailL.setError("Enter an Email")
                etEmailL.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(etPassL.text)){
                etPassL.setError("Enter a Password")
                etPassL.requestFocus()
                return@setOnClickListener
            }

            aM.userLogin(activity, context, etEmailL.text.toString(), etPassL.text.toString())
        }
    }
}
