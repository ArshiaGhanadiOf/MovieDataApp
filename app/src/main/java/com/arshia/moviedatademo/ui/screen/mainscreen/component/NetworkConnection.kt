package com.arshia.moviedatademo.ui.screen.mainscreen.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.arshia.moviedatademo.helper.ConnectionStatus
import com.arshia.moviedatademo.helper.currentConnectionStatus
import com.arshia.moviedatademo.helper.observeConnectivityAsFlow

@Composable
fun connectivityStatus(): State<ConnectionStatus> {
    val mCtx = LocalContext.current

    return produceState(initialValue = mCtx.currentConnectionStatus) {
        mCtx.observeConnectivityAsFlow().collect { value = it }
    }
}