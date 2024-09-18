package com.arshia.moviedatademo.helper

sealed class ConnectionStatus {
    object Available: ConnectionStatus()
    object Unavailable: ConnectionStatus()
}