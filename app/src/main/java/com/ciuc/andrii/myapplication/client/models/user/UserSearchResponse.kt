package com.ciuc.andrii.myapplication.client.models.user

import com.google.gson.annotations.SerializedName

data class UserSearchResponse (
	@SerializedName("total_count") val total_count : Int,
	@SerializedName("incomplete_results") val incomplete_results : Boolean,
	@SerializedName("items") val items : List<UserSearchItem>
)