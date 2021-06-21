package com.moreinfo.sales.model


import com.google.gson.annotations.SerializedName

data class SearchUser(
    @SerializedName("cust_id")
    val custId: String?,
    @SerializedName("cust_name")
    val custName: String?
)