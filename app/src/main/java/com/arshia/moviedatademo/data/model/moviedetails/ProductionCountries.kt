package com.arshia.moviedatademo.data.model.moviedetails

import com.google.gson.annotations.SerializedName

data class ProductionCountries(
    @SerializedName("iso_3166_1")
    val iso31661:String,
    val name:String

)
