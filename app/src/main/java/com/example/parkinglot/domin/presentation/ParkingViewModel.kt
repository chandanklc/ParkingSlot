package com.example.parkinglot.domin.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.parkinglot.domin.Vehicle
import com.example.parkinglot.domin.usecase.CreateParkingUseCase
import com.example.parkinglot.domin.usecase.GetStatusUseCase
import com.example.parkinglot.domin.usecase.ParkVehicleUseCase
import com.example.parkinglot.domin.usecase.ReleaseVehicleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ParkingViewModel(
    private val createParkingUseCase: CreateParkingUseCase,
    private val parkVehicleUseCase: ParkVehicleUseCase,
    private val releaseVehicleUseCase: ReleaseVehicleUseCase,
    private val getStatusUseCase: GetStatusUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow<ParkingUiState>(ParkingUiState.Ideal)
    val uiState = _uiState.asStateFlow()

    val state = getStatusUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun createParking(size: Int) = viewModelScope.launch {
        _uiState.value = ParkingUiState.Loading
        try {
            createParkingUseCase.invoke(size)
        } catch (e: Exception) {
            _uiState.value = ParkingUiState.Error(e.message ?: "Unknown error")
        }
    }

    fun parkVehicle(registerNumber: String, color: String) = viewModelScope.launch {
        _uiState.value = ParkingUiState.Loading
        try {
            val result = parkVehicleUseCase(Vehicle(registerNumber,color))
            _uiState.value =  ParkingUiState.Success("Allocated spot ${result.getOrNull()}")
        } catch (e: Exception) {
            _uiState.value = ParkingUiState.Error(e.message ?: "Unknown error")
        }
    }
    fun releaseSpot(num: Int) = viewModelScope.launch {
        _uiState.value = ParkingUiState.Loading
        val res = releaseVehicleUseCase(num)
        _uiState.value = if (res.isSuccess)
            ParkingUiState.Success("Spot $num is now free")
        else ParkingUiState.Error(res.exceptionOrNull()?.message ?: "Release failed")
    }
}
