package test.application.grafic.ui.activity

import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import test.application.grafic.base.BaseViewModel
import test.application.grafic.di.IoDispatcher
import test.application.grafic.domain.GetCoordinatesUseCase
import test.application.grafic.network.ErrorApiResult
import test.application.grafic.network.SuccessApiResult
import test.application.grafic.ui.MainViewModelDelegate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getCoordinatesUseCase: GetCoordinatesUseCase,
    mainViewModelDelegate: MainViewModelDelegate
) : BaseViewModel(), MainViewModelDelegate by mainViewModelDelegate {

    fun fetchPoints(count: Int) = liveData(ioDispatcher) {
            progressBarState.emit(true)
            when (val result = getCoordinatesUseCase.invoke(count)) {
                is SuccessApiResult -> {
                    this@liveData.emit(result.data)
                    setPoints(result.data)
                }

                is ErrorApiResult -> {
                    errorApiResult.emit(result)
                }
            }
            progressBarState.emit(false)
        }
//    }
}