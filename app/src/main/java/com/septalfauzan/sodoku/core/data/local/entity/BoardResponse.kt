package com.septalfauzan.sodoku.core.data.local.entity

import com.google.gson.annotations.SerializedName

data class BoardResponse(
	@field:SerializedName("board")
	val board: List<List<Int>>
)