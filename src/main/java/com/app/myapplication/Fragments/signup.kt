package com.app.myapplication.Fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.myapplication.AuthManager
import com.app.myapplication.R
import kotlinx.android.synthetic.main.fragment_signup.*


class signup : Fragment() {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        registerBtnAccept.setOnClickListener {


            if(TextUtils.isEmpty(etNameS.text)){
                etNameS.setError("Enter a Username")
                etNameS.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(etEmailS.text)){
                etEmailS.setError("Enter an Email")
                etEmailS.requestFocus()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(etPassS.text)){
                etPassS.setError("Enter a Password")
                etPassS.requestFocus()
                return@setOnClickListener
            }

            aM.registration(activity, context, etNameS.text.toString(), etEmailS.text.toString(), etPassS.text.toString())
        }
    }
}
