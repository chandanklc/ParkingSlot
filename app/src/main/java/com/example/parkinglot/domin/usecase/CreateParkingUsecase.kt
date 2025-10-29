package com.example.parkinglot.domin.usecase

import com.example.parkinglot.domin.repository.ParkingRepository

class CreateParkingUseCase(val repository: ParkingRepository) {
    suspend operator fun invoke(size: Int) = repository.createParking(size)
}
