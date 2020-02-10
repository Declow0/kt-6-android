package ru.netology.model

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

infix fun Double.x(that: Double) = Location(this, that)