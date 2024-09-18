package com.arshia.moviedatademo.data.model.persondetails

import com.arshia.moviedatademo.data.model.ItemMovie

data class PersonCredits(
    val cast: List<ItemMovie>,
    val crew: List<ItemMovie>,
    val id: Int
)
