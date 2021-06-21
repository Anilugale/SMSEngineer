package com.moreinfo.sales.cache

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.moreinfo.sales.model.City
import com.moreinfo.sales.model.StateModel
import com.moreinfo.sales.util.KUtility
import com.moreinfo.sales.util.SettingPreferencesUtility
import java.lang.Exception
import java.lang.reflect.Type

object DataCache {
    const val cityListC = "cityList"
    val cityListType: Type = object : TypeToken<ArrayList<City>>() {}.type
    var cityList: java.util.ArrayList<City> = ArrayList<City>()

    const val stateListC = "stateList"
    val stateListType: Type = object : TypeToken<ArrayList<StateModel>>() {}.type
    var stateList: java.util.ArrayList<StateModel> = ArrayList<StateModel>()

    fun saveCityList(userString:String,context: Context){
        SettingPreferencesUtility.get(context).edit().putString(cityListC,userString).apply()
        initCityList(context)
    }


    fun initCityList(context: Context) {
        val string = SettingPreferencesUtility.get(context).getString(cityListC, "")
        if(string!=null && string.isNotEmpty()){
            try {
                cityList =  KUtility.gson.fromJson<ArrayList<City>>(string,cityListType )
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun saveStatusList(userString:String,context: Context){
        SettingPreferencesUtility.get(context).edit().putString(stateListC,userString).apply()
        initStateList(context)
    }


    fun initStateList(context: Context) {
        val string = SettingPreferencesUtility.get(context).getString(stateListC, "")
        if(string!=null && string.isNotEmpty()){
            try {
                stateList =  KUtility.gson.fromJson<ArrayList<StateModel>>(string, stateListType )
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}