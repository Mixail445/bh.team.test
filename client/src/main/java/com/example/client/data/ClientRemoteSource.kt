package com.example.client.data

interface ClientRemoteSource {
    suspend fun getGesture(url:String):String
}