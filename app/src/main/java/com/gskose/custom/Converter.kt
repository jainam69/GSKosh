@file:JvmName("Converter")

package com.gskose.custom

fun convertListToString(listString: List<String>): String {
    var response = ""
    listString.forEach {
        response += "$it,"
    }
    return response.substring(0, response.length - 1)
}