package com.moreinfo.enginnerapp.util

import android.content.Context
import android.content.SharedPreferences

object SettingPreferencesUtility {
    var sharedPreferences:SharedPreferences?=null
    fun  create(context:Context){
         sharedPreferences = context.getSharedPreferences(Constants.SETTINGS_SHARED_PREF_NAME, 0)
    }
    fun get(context:Context):SharedPreferences{
        if (sharedPreferences == null) {
            create(context)
        }
        return sharedPreferences!!
    }
}