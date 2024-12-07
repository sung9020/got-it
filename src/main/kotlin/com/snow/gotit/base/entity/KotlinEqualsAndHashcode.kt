package com.snow.gotit.base.entity

import kotlin.reflect.KProperty1

@Suppress("UNCHECKED_CAST")
fun <T: Any> T.kotlinEquals(other: Any?, properties: List<KProperty1<T, *>>): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    return properties.all { property ->
        property.get(this) == property.get(other as T)
    }
}

fun <T: Any> T.kotlinHashCode(properties: List<KProperty1<T, *>>): Int {
    var hashcode= 0
    for (property in properties) {
        val fieldValue = property.get(this)
        val fieldHashCode = fieldValue?.hashCode() ?: 0
        hashcode = 31 * hashcode + fieldHashCode
    }

    return hashcode;
}