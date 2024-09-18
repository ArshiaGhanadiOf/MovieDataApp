package com.arshia.moviedatademo.ui.screens.mainscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arshia.moviedatademo.R
import com.arshia.moviedatademo.ui.theme.LightningYellow
import com.arshia.moviedatademo.ui.theme.failureBackgroundBrush

@Composable
fun LostConnectionUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(failureBackgroundBrush),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "No Internet",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 20.dp),
            colorFilter = ColorFilter.tint(LightningYellow)
        )
        Text(
            text = "Internet is off...",
            fontSize = 32.sp,
            color = LightningYellow
        )
    }
}
