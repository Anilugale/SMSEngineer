package com.moreinfo.sales.util

import android.content.*
import android.util.Log
import com.moreinfo.sales.cache.DataCache
import okhttp3.*

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.*
import java.util.concurrent.TimeUnit


object RequestUtility {
    const val TAG = "RequestUtility"
    private val client :OkHttpClient = OkHttpClient.Builder()
       .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
       .writeTimeout(30, TimeUnit.SECONDS) // write timeout
       .readTimeout(30, TimeUnit.SECONDS) // read timeout
        .cache(null)
       .build()
    val JSON by lazy { "application/json; charset=utf-8".toMediaTypeOrNull() }




    fun login(json: JSONObject, callback: Callback) {
        Log.d(TAG, "remove address: ${UrlUtility.login}")
        val body = RequestBody.create(JSON, json.toString())
        val request = Request.Builder()
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .url(UrlUtility.login)
            .post(body)
            .build()
        with(client) {
            newCall(request).enqueue(callback)
        }
    }


    fun getCitys( context: Context) {
        Log.d(TAG, "city List address: ${UrlUtility.cityList}")
        val request = Request.Builder()
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .url(UrlUtility.cityList)
            .get()
            .build()
        with(client) {
            newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val string = response.body!!.string()
                    try {
                        val json= JSONObject(string)
                        if(json.getString("status").equals("True",true)){
                            DataCache.saveCityList(json.getString("Data"),context)
                        }

                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }
            })
        }
    }



    fun getStateList( context: Context) {
        Log.d(TAG, "city List address: ${UrlUtility.statusList}")
        val request = Request.Builder()
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .url(UrlUtility.statusList)
            .get()
            .build()
        with(client) {
            newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val string = response.body!!.string()
                    try {
                        val json= JSONObject(string)
                        if(json.getString("status").equals("True",true)){
                            DataCache.saveStatusList(json.getString("Data"),context)
                        }

                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }
            })
        }
    }

    fun getUserDetails(reqJson: JSONObject,callback: Callback) {
        Log.d(TAG, "getUserDetails: ${UrlUtility.getUserDetails}")
        val body = RequestBody.create(JSON, reqJson.toString())
        val request = Request.Builder()
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .url(UrlUtility.getUserDetails)
            .post(body)
            .build()
        with(client) {
            newCall(request).enqueue(callback)
        }
    }

    fun registerUser(reqJson: JSONObject,callback: Callback) {
        Log.d(TAG, "registerUser: ${UrlUtility.registrationUrl}")
        val body = RequestBody.create(JSON, reqJson.toString())
        val request = Request.Builder()
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .url(UrlUtility.registrationUrl)
            .post(body)
            .build()
        with(client) {
            newCall(request).enqueue(callback)
        }
    }

}