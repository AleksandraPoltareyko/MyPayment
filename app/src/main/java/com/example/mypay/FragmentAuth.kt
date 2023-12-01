package com.example.mypay

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


class FragmentAuth: Fragment() {

    private lateinit var buttonAuth: Button
    private lateinit var loginEdit: EditText
    private lateinit var passwordEdit: EditText
    private var authActivity: AuthFinishedListener? = null
    private var login:String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_auth, container, false)

        buttonAuth = view.findViewById(R.id.button_auth)
        loginEdit = view.findViewById(R.id.login)
        passwordEdit = view.findViewById(R.id.password)
        buttonAuth.setOnClickListener {
            login = loginEdit.text.toString()
             password = passwordEdit.text.toString()
            val isOK: Boolean = MainActivity.db.getUser(login, password )
            if (isOK){
                authActivity?.authFinished(login, password)
            }else{
                Toast.makeText(activity, "Некорректно введены логин или пароль", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    interface AuthFinishedListener {

        fun authFinished(login: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthFinishedListener) {
            authActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        authActivity = null
    }

}