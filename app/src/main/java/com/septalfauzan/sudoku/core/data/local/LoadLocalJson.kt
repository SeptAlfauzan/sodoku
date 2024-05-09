package com.septalfauzan.sudoku.core.data.local

import com.google.gson.Gson
import com.septalfauzan.sudoku.core.data.local.entity.BoardResponse

object LoadLocalJson {
    fun getBoard(jsonStr: String): BoardResponse {
        try {
            val gson = Gson()
            return gson.fromJson(jsonStr, BoardResponse::class.java)
        }catch (e: Exception){
            throw e
        }
    }
}