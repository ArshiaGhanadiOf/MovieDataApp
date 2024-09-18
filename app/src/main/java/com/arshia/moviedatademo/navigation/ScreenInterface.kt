package com.arshia.moviedatademo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface ScreenInterface {
    val route: String

    @get:StringRes
    val title: Int?

    @get:DrawableRes
    val navIcon: Int?

    @get:DrawableRes
    val navIconSelected: Int?
    val objectName: String?
    val objectPath: String?
}