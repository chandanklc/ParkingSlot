package com.example.parkinglot.domin

import com.example.parkinglot.domin.datasource.InMemoryDataSource
import com.example.parkinglot.domin.presentation.ParkingViewModel
import com.example.parkinglot.domin.repository.ParkingRepository
import com.example.parkinglot.domin.repository.ParkingRepositoryImpl
import com.example.parkinglot.domin.usecase.CreateParkingUseCase
import com.example.parkinglot.domin.usecase.GetStatusUseCase
import com.example.parkinglot.domin.usecase.ParkVehicleUseCase
import com.example.parkinglot.domin.usecase.ReleaseVehicleUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single{ InMemoryDataSource() }
    single<ParkingRepository> { ParkingRepositoryImpl(get()) }

    factory{ CreateParkingUseCase(get()) }
    factory{ ParkVehicleUseCase(get()) }
    factory{ ReleaseVehicleUseCase(get()) }
    factory{ GetStatusUseCase(get()) }
    viewModel{
        ParkingViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
}
