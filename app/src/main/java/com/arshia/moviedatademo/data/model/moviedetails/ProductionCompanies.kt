package com.arshia.moviedatademo.data.model.moviedetails

import com.google.gson.annotations.SerializedName

data class ProductionCompanies(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)
