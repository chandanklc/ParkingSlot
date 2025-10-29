package com.example.parkinglot.domin

data class ParkingSpot(
    val number: Int,
    val isOccupied : Boolean = false,
    val vehicle : Vehicle ? = null
)
