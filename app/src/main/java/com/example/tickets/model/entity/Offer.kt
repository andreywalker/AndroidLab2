package com.example.tickets.model.entity

data class Offer(
    val id: String,
    val price: Int,
    val flight: Flight
)