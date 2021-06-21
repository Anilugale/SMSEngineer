package com.moreinfo.sales.model

import com.google.gson.annotations.SerializedName

data class StateModel(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("country_id")
	val countryId: String? = null
)
