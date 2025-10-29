🚗 Parking Lot App — Clean Architecture (MVVM + Koin + WorkManager + Compose)

A sample Parking Lot Management app built using Clean Architecture, MVVM, Jetpack Compose, Koin Dependency Injection, and WorkManager.
This app demonstrates modern Android development best practices with a modular, scalable, and testable structure.

🧩 Features

🅿️ Manage parking spots (free vs occupied)

🚘 Assign and remove vehicles dynamically

🎨 Beautiful UI with Jetpack Compose and dynamic color indicators

🔄 Koin for dependency injection

🧠 MVVM + Clean Architecture for clear separation of concerns

🔔 WorkManager integration to send hourly parking notifications

⚙️ Uses Kotlin Flows for reactive UI updates



🧩 Project Structure
com.example.parkinglot
│
├── data
│   ├── repository
│   │   └── ParkingRepositoryImpl.kt
│
├── domain
│   ├── model
│   │   ├── ParkingSpot.kt
│   │   └── Vehicle.kt
│   ├── repository
│   │   └── ParkingRepository.kt
│   └── usecase
│       └── GetParkingStatusUseCase.kt
│
├── presentation
│   ├── viewmodel
│   │   └── ParkingViewModel.kt
│   ├── ui
│   │   ├── ParkingScreen.kt
│   │   └── components
│   │       └── ParkingBox.kt
│
├── worker
│   └── ParkingStatusWorker.kt
│
└── di
    └── AppModule.kt
