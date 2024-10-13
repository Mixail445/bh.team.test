package com.example.client.data

import com.example.common.DispatchersProvider
import kotlinx.coroutines.withContext

class RemoteSourceImpl(private val api: Api, private val dispatchersProvider: DispatchersProvider) :
    ClientRemoteSource {
    override suspend fun getGesture(url: String): String = withContext(dispatchersProvider.io) {
        api.getGesture(url)
    }

}