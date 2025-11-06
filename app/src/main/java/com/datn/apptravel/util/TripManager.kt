package com.datn.apptravel.util

import java.util.UUID

data class Trip(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String? = null,
    val price: String? = "75.000.000đ",
    val description: String? = null,
    val isSaved: Boolean = false
)

class TripManager {
    private val trips = mutableListOf<Trip>()

    fun createSampleTrip() {
        // Add sample trips
        val parisTrip = Trip(
            title = "Sample Trip to Paris",
            destination = "Paris, France",
            startDate = "2025-10-15",
            endDate = "2025-10-25",
            description = "Explore the beautiful city of Paris with guided tours to famous landmarks."
        )
        
        val barrierReefTrip = Trip(
            title = "Visit the Great Barrier Reef",
            destination = "Queensland, Australia",
            startDate = "2025-11-05",
            endDate = "2025-11-15",
            description = "Explore the world's largest coral reef system with diving and boat tours."
        )
        
        val tokyoTrip = Trip(
            title = "Tokyo Adventure",
            destination = "Tokyo, Japan",
            startDate = "2025-12-10",
            endDate = "2025-12-20",
            description = "Experience the blend of traditional and modern culture in Tokyo."
        )
        
        trips.add(parisTrip)
        trips.add(barrierReefTrip)
        trips.add(tokyoTrip)
    }
    

    fun getAllTrips(): List<Trip> {
        return trips.toList()
    }

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }
    

    fun deleteTrip(tripId: String) {
        trips.removeIf { it.id == tripId }
    }

    fun updateTrip(updatedTrip: Trip) {
        val index = trips.indexOfFirst { it.id == updatedTrip.id }
        if (index != -1) {
            trips[index] = updatedTrip
        }
    }

    fun getTripById(tripId: String): Trip? {
        return trips.find { it.id == tripId }
    }

    fun toggleSavedStatus(tripId: String) {
        val index = trips.indexOfFirst { it.id == tripId }
        if (index != -1) {
            val trip = trips[index]
            trips[index] = trip.copy(isSaved = !trip.isSaved)
        }
    }
}