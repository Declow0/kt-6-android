package ru.netology.model

data class Location(
    val latitude: Double,
    val longitude: Double
)

infix fun Double.x(that: Double) = Location(this, that)