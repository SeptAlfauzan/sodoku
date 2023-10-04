package com.septalfauzan.sodoku.core.data.local

import android.content.Context
import com.google.gson.Gson
import com.septalfauzan.sodoku.R
import com.septalfauzan.sodoku.core.data.local.entity.BoardResponse

object LoadLocalJson {
    fun getBoard(context: Context): BoardResponse {
        val gson = Gson()
        val json = context.resources.openRawResource(R.raw.empty_board).bufferedReader().use { it.readText() }
        return gson.fromJson(json, BoardResponse::class.java)
    }
}