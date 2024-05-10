package com.septalfauzan.sudoku.helper

fun formatTimer(timeInSecond: Int): String{
    val minute = timeInSecond / 60
    val second = timeInSecond % 60
    val minuteStr = if(minute < 10) "0$minute" else minute.toString()
    val secondStr = if(second < 10) "0$second" else second.toString()
    return "$minuteStr:$secondStr"
}