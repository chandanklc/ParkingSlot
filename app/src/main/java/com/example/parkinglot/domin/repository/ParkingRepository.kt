package com.example.parkinglot.domin.repository

import com.example.parkinglot.domin.ParkingSpot
import com.example.parkinglot.domin.Vehicle
import kotlinx.coroutines.flow.Flow

interface ParkingRepository {
    suspend fun createParking(size: Int)
    suspend fun parkVehicle(vehicle: Vehicle): Result<Int>
    suspend fun releaseSpot(spotNumber: Int): Result<Unit>
    fun getStatus(): Flow<List<ParkingSpot>>
}
