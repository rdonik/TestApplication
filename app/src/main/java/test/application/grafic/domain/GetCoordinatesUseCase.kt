package test.application.grafic.domain

import kotlinx.coroutines.CoroutineDispatcher
import test.application.grafic.base.UseCase
import test.application.grafic.data.point.PointResponse
import test.application.grafic.di.IoDispatcher
import test.application.grafic.network.NetworkResult
import test.application.grafic.network.repositorys.PointsRepository
import test.application.grafic.shared.result.getResult
import javax.inject.Inject

class GetCoordinatesUseCase @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val pointsRepository: PointsRepository
) : UseCase<Int, NetworkResult<PointResponse>>(ioDispatcher) {

    override suspend fun performAction(parameters: Int): NetworkResult<PointResponse> {
        return getResult { pointsRepository.checkPoints(parameters) }
    }

}