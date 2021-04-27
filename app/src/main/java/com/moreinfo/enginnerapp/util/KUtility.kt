package com.moreinfo.enginnerapp.util

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.format.DateUtils
import android.util.Log
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.isHide():Boolean{
    return this.visibility == View.GONE
}


fun View.isShowing():Boolean{
    return this.visibility == View.VISIBLE
}


val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
object KUtility {

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun showAlert(context: Context?, message: String) {
        if (context != null) {
            MaterialAlertDialogBuilder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
        }

    }

    fun getCurrentIsoToRelative(dateString: String): String {
        val format = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
        format.timeZone = TimeZone.getTimeZone("UTC")
        try {
            val date: Date = format.parse(dateString)
            println(date)
            return DateUtils.getRelativeTimeSpanString(date.time).toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateString
    }

    val gson = Gson()
}



