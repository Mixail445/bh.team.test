package com.example.client.domain

import com.example.common.AppResult

interface ClientRepository {
    suspend fun getGesture(url: String): AppResult<String, Throwable>
}