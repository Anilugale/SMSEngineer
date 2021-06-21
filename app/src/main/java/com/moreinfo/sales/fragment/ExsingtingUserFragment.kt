package com.moreinfo.sales.fragment

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.moreinfo.sales.DashboardActivity
import com.moreinfo.sales.R
import com.moreinfo.sales.model.SearchUser
import com.moreinfo.sales.util.*
import kotlinx.android.synthetic.main.existing_user.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ExistingUserFragment : BaseFragment() {
    companion object{
        const val TAG = "ExistingUserFragment"
    }
    override fun getFragmentTitle(): String {
       return "Existing User"
    }

    override fun getLayoutId(): Int {
        return R.layout.existing_user
    }

    override fun initUI() {
        mobile.editText!!.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchBtn.isEnabled = s!!.isNotEmpty()
                if(s.isEmpty()){
                    userCard.hide()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        searchBtn.setOnClickListener {
            getUserDetails()
        }
    }

    private fun getUserDetails() {
        progressBar.show()
        val reqJson = JSONObject()
        val mobileNo = mobile.editText!!.text.toString()
        reqJson.put("cust_mob",mobileNo)
        RequestUtility.getUserDetails(reqJson,object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                GlobalScope.launch (Dispatchers.Main){
                    progressBar.hide()
                    Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                }

            }

            override fun onResponse(call: Call, response: Response) {
                val resultStr = response.body!!.string()
                val resultJson = JSONObject(resultStr)
                GlobalScope.launch (Dispatchers.Main){
                    progressBar.hide()
                    if(resultJson.getInt(Constants.RESPONSE_CODE) == 1 && !resultJson.getString(Constants.DATA).equals("Please register these number as customer") ){
                        val userList:ArrayList<SearchUser> = KUtility.gson.fromJson(resultJson.getString(Constants.DATA),Constants.searchUserListType)
                        if (userList.isNotEmpty()) {
                            showUser(userList[0])
                        }
                    }else{
                        Toast.makeText(requireContext(),resultJson.getString(Constants.RESPONSE_MESSAGE),Toast.LENGTH_SHORT).show()
                        (activity as DashboardActivity).attachRegistrationFragment(mobileNo)
                    }
                }
            }
        })
    }

    private fun showUser(searchUser: SearchUser) {
        userCard.show()
        nameUser.text = "Name : ${searchUser.custName}"
    }
}