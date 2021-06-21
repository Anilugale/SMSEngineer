package com.moreinfo.sales.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.moreinfo.sales.DashboardActivity
import com.moreinfo.sales.R
import com.moreinfo.sales.model.City
import com.moreinfo.sales.model.StateModel
import com.moreinfo.sales.scan.ScannerActivity
import com.moreinfo.sales.util.Constants
import com.moreinfo.sales.util.RequestUtility
import com.moreinfo.sales.util.show
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.list_state.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class Registration : BaseFragment() {

    var mobileArg = ""
    companion object{
        const val TAG = "Registration"
    }
    override fun getFragmentTitle(): String {
        return "Registration"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_registration
    }


    var selectedState:StateModel?=null
    var selectedCity:City?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobileArg = arguments!!.getString("mobile","")
    }
    override fun initUI() {
        if (mobileArg.isNotEmpty()) {
            mobile.editText!!.setText(mobileArg)
        }
        scanAdhar.setOnClickListener {
            val intent = Intent(context, ScannerActivity::class.java)
            intent.putExtra("odk_intent_data_field", "uid")
            intent.getBundleExtra("odk_intent_bundle")
            startActivityForResult(intent, 100)
        }

        stateName.setOnClickListener {
            showStateDialog()
        }
        cityName.setOnClickListener {
            showCityDialog()
        }
        register.setOnClickListener {
            registerUser()
        }
    }


    private fun showCityDialog() {

        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.list_state,null,false)
        layout.list.layoutManager  = LinearLayoutManager(requireContext())

        val show = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.city))
            .setView(layout)
            .show()

        layout.list.adapter = CityAdapter(requireContext()) {
            selectedCity = it
            cityName.text = it.name
            show?.dismiss()
        }
    }

    private fun showStateDialog() {

        val layout = LayoutInflater.from(requireContext()).inflate(R.layout.list_state,null,false)
        layout.list.layoutManager  = LinearLayoutManager(requireContext())

        val show = MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.state))
            .setView(layout)
            .show()

        layout.list.adapter = StateAdapter(requireContext()) {
            selectedState = it
            stateName.text = it.name
            show?.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null) {
            if (data.getBundleExtra("odk_intent_bundle") != null) {
                val bundleExtra = data.getBundleExtra("odk_intent_bundle")!!
                if (bundleExtra.containsKey("name")) {
                    nameUser.editText!!.setText(bundleExtra.getString("name"))
                }

                if (bundleExtra.containsKey("subdist")) {
                    cityName.text = bundleExtra.getString("subdist")
                }


                if (bundleExtra.containsKey("state")) {
                    stateName.text = bundleExtra.getString("state")
                }
                if (bundleExtra.containsKey("po")) {
                    pincode.editText!!.setText(bundleExtra.getString("po"))
                }

                if (bundleExtra.containsKey("pc")) {
                    pincode.editText!!.setText(bundleExtra.getString("pc"))
                }
                var addressTxt = ""
                if (bundleExtra.getString("house") != null) {
                    addressTxt = "\n"+bundleExtra.getString("house")!!
                }

                if (bundleExtra.getString("street") != null) {
                    addressTxt += "\n"+bundleExtra.getString("street")!!
                }

                if (bundleExtra.getString("lm") != null) {
                    addressTxt += "\n"+bundleExtra.getString("lm")!!
                }

                if (bundleExtra.getString("loc") != null) {
                    addressTxt += "\n"+bundleExtra.getString("loc")!!
                }
                if (bundleExtra.getString("vtc") != null) {
                    addressTxt += "\n"+bundleExtra.getString("vtc")!!
                }
                if (bundleExtra.getString("po") != null) {
                    addressTxt += "\n"+bundleExtra.getString("po")!!
                }

                address.editText!!.setText(addressTxt)

            }
        }
    }

    private fun registerUser() {
        clearError()
        if(nameUser.editText!!.text!!.isEmpty()){
            nameUser.error = "Name shouldn't black"
            nameUser.editText!!.requestFocus()
        }else if(mobile.editText!!.text!!.isEmpty()){
            mobile.error = "Mobile shouldn't black"
            mobile.editText!!.requestFocus()
        }else if(email.editText!!.text!!.isEmpty()){
            email.error = "Email shouldn't black"
            email.editText!!.requestFocus()
        }else if(password.editText!!.text!!.isEmpty()){
            password.error = "password shouldn't black"
            password.editText!!.requestFocus()
        }else if(address.editText!!.text!!.isEmpty()){
            address.error = "Address shouldn't black"
            address.editText!!.requestFocus()
        }else if(pincode.editText!!.text!!.isEmpty()){
            pincode.error = "Pin Code shouldn't black"
            pincode.editText!!.requestFocus()

        }else if(selectedState == null){
            Toast.makeText(requireContext(),"Select State!",Toast.LENGTH_LONG).show()

        }else if(selectedCity == null){
            Toast.makeText(requireContext(),"Select City!",Toast.LENGTH_LONG).show()

        }else {

            clearError()
            val jsonReq = JSONObject()
            jsonReq.put("cust_name", nameUser.editText!!.text!!.toString())
            jsonReq.put("cust_mob", mobile.editText!!.text)
            jsonReq.put("cust_email", email.editText!!.text)
            jsonReq.put("cust_pass", password.editText!!.text)
            jsonReq.put("address", address.editText!!.text)
            jsonReq.put("pincode", pincode.editText!!.text)
            jsonReq.put("country", "101")
            jsonReq.put("state", selectedState!!.id)
            jsonReq.put("cities", selectedCity!!.id)
            jsonReq.put("customer_type", "C")

            RequestUtility.registerUser(jsonReq,object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    GlobalScope.launch (Dispatchers.Main){
                        progressBar.show()
                        Toast.makeText(requireContext(),e.message,Toast.LENGTH_LONG).show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val stringResult = response.body!!.string()
                        val jsonResult = JSONObject(stringResult)
                        GlobalScope.launch (Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                jsonResult.getString(Constants.RESPONSE_MESSAGE),
                                Toast.LENGTH_LONG
                            ).show()
                            if (jsonResult.getInt(Constants.RESPONSE_CODE) == 1) {
                                (activity as DashboardActivity).attachExistingFragment()
                            }
                        }
                    }catch (e:Exception){
                        GlobalScope.launch (Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                e.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }


                }
            })


        }


    }
    fun clearError() {
        pincode.error = ""
        mobile.error = ""
        nameUser.error = ""
        email.error = ""
        password.error = ""
        address.error = ""
    }
}