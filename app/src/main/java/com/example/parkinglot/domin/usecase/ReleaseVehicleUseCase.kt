package com.example.parkinglot.domin.usecase

import com.example.parkinglot.domin.Vehicle
import com.example.parkinglot.domin.repository.ParkingRepository

class ReleaseVehicleUseCase(val repository: ParkingRepository) {
    suspend operator fun invoke(spot: Int) = repository.releaseSpot(spot)
}
