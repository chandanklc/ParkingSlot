package com.example.parkinglot.domin.presentation

sealed class ParkingUiState {
    object Ideal : ParkingUiState()
    object Loading : ParkingUiState()
    data class Success(val message: String) : ParkingUiState()
    data class Error(val error: String) : ParkingUiState()
}
