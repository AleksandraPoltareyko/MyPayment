package com.example.mypay

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AuthActivity:AppCompatActivity(), FragmentAuth.AuthFinishedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun authFinished(login: String, password: String) {
        val result = Intent()
        result.putExtra(MainActivity.LOGIN_NAME, login)
        result.putExtra(MainActivity.PASSWORD_NAME, password)
        setResult(RESULT_OK, result)
        finish()
    }
}