package com.septalfauzan.sodoku.helper

object DataMapper {
    fun List<List<Int>>.toDeepMutableList(): MutableList<MutableList<Int>> = this.map { it.toMutableList() }.toMutableList()
}