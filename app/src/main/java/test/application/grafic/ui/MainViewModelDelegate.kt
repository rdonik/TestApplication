package test.application.grafic.ui


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import test.application.grafic.data.point.PointResponse
import javax.inject.Inject
import javax.inject.Singleton

interface MainViewModelDelegate {

    val points: Flow<PointResponse?>
    suspend fun setPoints(item: PointResponse?)
}

@Singleton
class MainViewModelDelegateImpl @Inject constructor() : MainViewModelDelegate {

    private val _points = MutableStateFlow<PointResponse?>(null)

    override val points: Flow<PointResponse?> = _points

    override suspend fun setPoints(item: PointResponse?) {
        _points.value = item
    }
}