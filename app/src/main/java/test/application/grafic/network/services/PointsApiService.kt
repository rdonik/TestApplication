package test.application.grafic.network.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import test.application.grafic.data.point.PointResponse

interface PointsApiService {

    @GET("/api/test/points")
    suspend fun getCoordinate(
        @Query("count") pointsCount: Int
    ): Response<PointResponse>

}