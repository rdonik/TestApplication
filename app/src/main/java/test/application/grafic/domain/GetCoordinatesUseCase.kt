package test.application.grafic.domain

import kotlinx.coroutines.CoroutineDispatcher
import test.application.grafic.base.UseCase
import test.application.grafic.data.point.PointResponse
import test.application.grafic.di.IoDispatcher
import test.application.grafic.network.ErrorApiResult
import test.application.grafic.network.NetworkResult
import test.application.grafic.network.SuccessApiResult
import test.application.grafic.network.repositorys.PointsRepository
import test.application.grafic.shared.result.getResult
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(
    private val pointsRepository: PointsRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<Int, NetworkResult<PointResponse>>(ioDispatcher) {
    override suspend fun performAction(parameters: Int): NetworkResult<PointResponse> {
        return getResult { pointsRepository.checkPoints(parameters) }
    }
}