package com.example.crypocritic.utils

fun String?.nullCheck(): String {
    if (this != null && !this.isNullOrBlank() && this != "null") {
        return this
    }
    return "-"
}