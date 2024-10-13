package com.example.client.data

import com.example.client.domain.ClientRepository
import com.example.common.AppResult
import com.example.common.ResultWrapper

class ClientRepositoryImpl(
    private val wrapper: ResultWrapper,
    private val remoteSource: ClientRemoteSource
) : ClientRepository {
    override suspend fun getGesture(url: String): AppResult<String, Throwable> = wrapper.wrap {
        remoteSource.getGesture(url)
    }
}