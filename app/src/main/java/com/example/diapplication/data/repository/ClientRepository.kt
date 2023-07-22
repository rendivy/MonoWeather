package com.example.diapplication.data.repository

import com.example.diapplication.data.remote.ClientGeolocationService
import com.example.diapplication.domain.entity.UserGeocoding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(var clientGeolocationService: ClientGeolocationService)  {

    suspend fun getClientService(latitude: Double, longitude: Double): UserGeocoding {
        return clientGeolocationService.getCurrentGeolocation(latitude, longitude)
    }

}