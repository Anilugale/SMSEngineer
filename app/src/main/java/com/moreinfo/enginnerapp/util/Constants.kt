package com.moreinfo.enginnerapp.util

import com.google.gson.reflect.TypeToken
import com.moreinfo.enginnerapp.model.SearchUser
import java.lang.reflect.Type
import java.util.ArrayList

object Constants {

    const val SETTINGS_SHARED_PREF_NAME = "SETTINGS_SHARED_PREF_NAME"
    const val RESPONSE_MESSAGE = "ResponseMessage"
    const val DATA = "Data"
    const val RESPONSE_CODE = "ResponseCode"

    val searchUserListType: Type = object : TypeToken<ArrayList<SearchUser>>() {}.type
}