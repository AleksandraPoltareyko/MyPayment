package com.example.mypay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypay.data.BodyToken
import com.example.mypay.data.Payment
import com.example.mypay.data.ThePayment
import com.example.mypay.data.UserToken
import com.example.mypay.data.payments.Payments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        val LOGIN_NAME = "LOGIN_NAME"
        val PASSWORD_NAME = "PASSWORD_NAME"
        val NAME_REQUEST = 222
        var user: String = ""
        var password: String = ""
        lateinit var db: AuthHelper
    }

    private val myButton: TextView by lazy { findViewById(R.id.my_button) }
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://easypay.world/") // базовый url
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    private val service = retrofit.create(ThePayment::class.java)
    private var token = ""
    private var amountP = 0.0
    val listOfPayments = mutableListOf<Payment>()
    private lateinit var adapter : RecyclerPaymentAdapter
    private val list: RecyclerView by lazy { findViewById(R.id.list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AuthHelper(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (user.isEmpty())
            menuInflater.inflate(R.menu.login, menu)
        else
            menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_login ->{
                val authIntent = Intent(this, AuthActivity::class.java)

                startActivityForResult(authIntent, NAME_REQUEST)
                return true
            }
            R.id.menu_logout ->{
                user = ""
                invalidateOptionsMenu()

                myButton.visibility = View.INVISIBLE
                list.visibility = View.INVISIBLE
                listOfPayments.clear()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == NAME_REQUEST)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                user = data.getStringExtra(LOGIN_NAME) ?: ""
                password = data.getStringExtra(PASSWORD_NAME) ?: ""
                invalidateOptionsMenu()
                if (user.isEmpty()){
                    myButton.visibility = View.INVISIBLE
                    list.visibility = View.INVISIBLE
                }else{
                    myButton.visibility = View.VISIBLE
                    list.visibility = View.VISIBLE
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun getPayments(view: View){

        getToken()

    }


    private  fun getToken(){
        val call = service.getToken(BodyToken(user, password))

        lifecycleScope.launch(Dispatchers.Default)
        {
            try {
                val response = call.execute()
                withContext(Dispatchers.Main)
                {
                    onResponse(call, response)
                }
                val callPayment = service.getPayments(token)
                val responsePayment = callPayment.execute()
                withContext(Dispatchers.Main)
                {
                    onResponsePayments(callPayment, responsePayment)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main)
                {
                    onFailure(e)
                }

            }
        }
    }

    private fun onFailure( e: Exception) {

        Log.d("error", "onFailure: ${e.message}")

    }

    private fun onResponse(call: Call<UserToken>, response: Response<UserToken>) {

        if (response.isSuccessful) {
            val result = response.body()
            if (result != null) {
                token = if(result.success == "true"){
                    result.response.token
                }else {
                    ""
                }
            }
        }
    }

    private fun onResponsePayments(call: Call<Payments>, response: Response<Payments>) {

        if (response.isSuccessful) {
            val result = response.body()
            if (result != null) {
                listOfPayments.clear()
                if (result.success == "true") {
                    for (pay in result.response) {
                        amountP = try {
                            pay.amount?.toDouble() ?: 0.0
                        } catch (e: Exception){
                            0.0
                        }
                        listOfPayments.add(

                            Payment(
                                pay.id.toString(),
                                pay.title,
                                pay.created.toString(),
                                amountP
                            )
                        )
                    }
                    adapter = RecyclerPaymentAdapter(listOfPayments)
                    list.adapter = adapter
                    list.layoutManager = LinearLayoutManager(this)
                    list.addItemDecoration(
                        DividerItemDecoration(
                            this,
                            LinearLayoutManager.VERTICAL
                        )
                    )
                }
                adapter.notifyDataSetChanged()
                }
            }
        }
    }
