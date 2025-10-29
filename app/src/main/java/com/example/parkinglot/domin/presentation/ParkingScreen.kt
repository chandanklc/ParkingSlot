package com.example.parkinglot.domin.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.parkinglot.domin.ParkingSpot
import org.koin.androidx.compose.koinViewModel

@Composable
fun ParkingScreen(parkingViewModel: ParkingViewModel = koinViewModel()) {
    val uiState by parkingViewModel.uiState.collectAsState()
    val status by parkingViewModel.state.collectAsState()
    var slotInput by remember {
        mutableStateOf("")
    }
    var createSlotInput by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Parking systems".uppercase(), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        when (uiState) {
            is ParkingUiState.Error -> Text(
                (uiState as ParkingUiState.Error).error,
                color = Color.Red
            )

            ParkingUiState.Loading -> Text("Loading....")
            is ParkingUiState.Success -> Text((uiState as ParkingUiState.Success).message)

            else -> {}
        }

        Spacer(Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(end = 16.dp),
                    value = createSlotInput,
                    onValueChange = { newValue: String ->
                        if (newValue.all { it.isDigit() }) {
                            createSlotInput = newValue
                        }

                    },
                    placeholder = {
                        Text(
                            text = "Enter slot to clear",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                )
                Button(onClick = {
                    val createSlot = createSlotInput.toIntOrNull()
                    if (createSlot != null) {
                        parkingViewModel.createParking(createSlotInput.toInt())
                        createSlotInput = ""
                    }

                }) {
                    Text("Created $createSlotInput slots")
                }
            }
            Button(onClick = {
                parkingViewModel.parkVehicle(
                    "KA-01-AB-1234",
                    "Red"
                )
            }) { Text("Park Vehicle") }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .padding(end = 16.dp),
                    value = slotInput,
                    onValueChange = { newValue: String ->
                        if (newValue.all { it.isDigit() }) {
                            slotInput = newValue
                        }

                    },
                    placeholder = {
                        Text(
                            text = "Enter slot to clear",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                )
                Button(onClick = {
                    val slotNumber = slotInput.toIntOrNull()
                    if (slotNumber != null) {
                        parkingViewModel.releaseSpot(slotNumber)
                        slotInput = ""
                    }

                }) {
                    Text("Release Spot $slotInput")
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("Parking Status", style = MaterialTheme.typography.bodySmall)

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(status.size) { index ->
                ParkingBox(status[index])
            }
        }
    }
}

@Composable
fun ParkingBox(spot: ParkingSpot) {
    val backgroundColor = if (spot.isOccupied) Color.Red else Color.Green
    val textColor = Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .padding(8.dp)
    ) {

        Text(
            text = "Spot ${spot.number}",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.height(4.dp))
        if (spot.isOccupied) {
            Text(
                text = spot.vehicle?.registerNumber ?: "",
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = spot.vehicle?.color ?: "",
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        } else {
            Text(
                text = "Free",
                color = textColor,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}
