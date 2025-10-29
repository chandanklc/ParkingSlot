package com.example.parkinglot.domin.usecase

import com.example.parkinglot.domin.repository.ParkingRepository

class GetStatusUseCase(val repository: ParkingRepository) {
    operator fun invoke() = repository.getStatus()
}
