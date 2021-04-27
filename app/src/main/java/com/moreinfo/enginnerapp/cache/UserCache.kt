package com.moreinfo.enginnerapp.cache

import android.content.Context
import android.provider.ContactsContract
import com.moreinfo.enginnerapp.model.User
import com.moreinfo.enginnerapp.util.KUtility
import com.moreinfo.enginnerapp.util.SettingPreferencesUtility

object UserCache {

    const val USER = "USER"
    var user:User?=null
    fun saveUser(userString:String,context: Context){
        SettingPreferencesUtility.get(context).edit().putString(USER,userString).apply()
        initUser(context)
    }

    fun initUser(context: Context) {
        val string = SettingPreferencesUtility.get(context).getString(USER, "")
        if(string!=null && string.isNotEmpty()){
            user =  KUtility.gson.fromJson<User>(string, User::class.java)
        }
        DataCache.initCityList(context)
        DataCache.initStateList(context)
    }
}