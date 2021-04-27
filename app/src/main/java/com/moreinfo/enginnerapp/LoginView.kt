package com.moreinfo.enginnerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.moreinfo.enginnerapp.cache.UserCache
import com.moreinfo.enginnerapp.util.RequestUtility
import com.moreinfo.enginnerapp.util.hide
import com.moreinfo.enginnerapp.util.show
import kotlinx.android.synthetic.main.activity_login_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class LoginView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_view)
        init()
    }

    private fun init() {
        password.editText!!.addTextChangedListener(watcher)
        username.editText!!.addTextChangedListener(watcher)
        login.setOnClickListener {
            handleLogin()
        }
    }

    var watcher = object:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            login.isEnabled =  password.editText!!.text.isNotEmpty() && username.editText!!.text.isNotEmpty()

        }

    }

    private fun handleLogin() {
        val jsonReq = JSONObject()
        jsonReq.put("contact_number",username.editText!!.text.toString())
        jsonReq.put("sale_pass",password.editText!!.text.toString())
        jsonReq.put("user_type","S")
        progressBar.show()
        RequestUtility.login(jsonReq, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.hide()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val string = response.body!!.string()

                val jsonResult = JSONObject(string)
                GlobalScope.launch(Dispatchers.Main) {
                    progressBar.hide()
                    Log.d("@@", "onResponse: $string")
                    if (jsonResult.getString("ResponseCode").equals("1")) {
                        UserCache.saveUser(jsonResult.getString("Data"),this@LoginView)
                        startActivity(Intent(this@LoginView,DashboardActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this@LoginView,jsonResult.getString("Data"),Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }
}