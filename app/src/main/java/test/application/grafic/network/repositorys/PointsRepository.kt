package test.application.grafic.network.repositorys

import retrofit2.Response
import test.application.grafic.data.point.PointResponse
import test.application.grafic.network.services.PointsApiService


interface PointsRepository {

    suspend fun checkPoints(pointCounts: Int): Response<PointResponse>

}

class PointsRepositoryImpl(private val pointsApiService: PointsApiService) : PointsRepository {

    override suspend fun checkPoints(pointCounts: Int) =
        pointsApiService.getCoordinate(pointsCount = pointCounts)

}
