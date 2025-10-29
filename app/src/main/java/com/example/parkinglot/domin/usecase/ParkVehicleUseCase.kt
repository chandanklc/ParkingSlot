package com.example.parkinglot.domin.usecase

import com.example.parkinglot.domin.Vehicle
import com.example.parkinglot.domin.repository.ParkingRepository

class ParkVehicleUseCase(val repository: ParkingRepository) {
    suspend operator fun invoke(vehicle: Vehicle) = repository.parkVehicle(vehicle)
}
