package com.example.parkinglot.domin.datasource

import androidx.compose.runtime.mutableStateOf
import com.example.parkinglot.domin.ParkingSpot
import com.example.parkinglot.domin.Vehicle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryDataSource {
    private val spot = mutableListOf<ParkingSpot>()
    private val _status = MutableStateFlow<List<ParkingSpot>>(emptyList())
    val status = _status.asStateFlow()

    private val mutex = Mutex()
    suspend fun createParking(size: Int) {
        mutex.withLock {
            spot.clear()
            repeat(size) { size ->
                spot.add(ParkingSpot(number = size + 1))
            }
            _status.value = spot.toList()
        }
    }

    suspend fun park(vehicle: Vehicle): Result<Int> = mutex.withLock {
        val freeSpotIndex = spot.indexOfFirst { !it.isOccupied }
        return if (freeSpotIndex == -1) {
            Result.failure(Exception("No Parking"))
        } else {
            val spotNumber = spot[freeSpotIndex].number
            spot[freeSpotIndex] = ParkingSpot(spotNumber, isOccupied = true, vehicle)
            _status.value = spot.toList()
            Result.success(spotNumber)
        }
    }

    suspend fun releaseParking(spotNumber: Int): Result<Unit> = mutex.withLock {
        val spotIndex = spot.indexOfFirst { it.number == spotNumber }
        if (spotIndex == -1) {
            return Result.failure(Exception("Invalid Spot"))
        }
        spot[spotIndex] = ParkingSpot(number = spotNumber, isOccupied = false, vehicle = null)
        _status.value = spot.toList()
        Result.success(Unit)
    }
}
