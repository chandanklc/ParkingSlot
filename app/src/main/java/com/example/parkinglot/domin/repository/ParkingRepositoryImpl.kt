package com.example.parkinglot.domin.repository

import com.example.parkinglot.domin.ParkingSpot
import com.example.parkinglot.domin.Vehicle
import com.example.parkinglot.domin.datasource.InMemoryDataSource
import kotlinx.coroutines.flow.Flow

class ParkingRepositoryImpl(
    val dataSource: InMemoryDataSource
) : ParkingRepository {
    override suspend fun createParking(size: Int) = dataSource.createParking(size)

    override suspend fun parkVehicle(vehicle: Vehicle) = dataSource.park(vehicle)

    override suspend fun releaseSpot(spotNumber: Int) = dataSource.releaseParking(spotNumber)

    override fun getStatus(): Flow<List<ParkingSpot>> = dataSource.status
}
