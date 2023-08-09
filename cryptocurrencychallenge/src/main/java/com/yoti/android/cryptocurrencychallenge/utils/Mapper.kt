package com.yoti.android.cryptocurrencychallenge.utils

abstract class Mapper<FROM, TO> {
    abstract fun mapTo(input: FROM): TO
    open fun mapTo(input: List<FROM>): List<TO> {
        return input.map {
            mapTo(it)
        }
    }
}