package com.example.tickets.model.service

import com.example.tickets.model.network.Dog
import java.util.UUID

object FakeService {

    var dogList = listOf(
        Dog(
            id = UUID.randomUUID().toString(),
            name="brad",
            min_life_expectancy = "2",
            trainability = "3",
            protectiveness = "3",
            energy="3",
            image_link = "https://media.istockphoto.com/id/1345494606/vector/no-dogs-allowed-dog-prohibition-sign-vector-illustration.jpg?s=612x612&w=0&k=20&c=RF03Spt3yKLJ0N6myYSqkq7UyPIeHddbyPsUdaXn_gA="

        )
    )
}