package com.moreinfo.enginnerapp.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("sale_id")
	val saleId: String,

	@field:SerializedName("sale_pass")
	val salePass: String,

	@field:SerializedName("company_id")
	val companyId: String,

	@field:SerializedName("contact_number")
	val contactNumber: String
)