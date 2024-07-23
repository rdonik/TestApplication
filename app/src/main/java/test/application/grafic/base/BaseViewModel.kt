package test.application.grafic.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import test.application.grafic.network.ErrorApiResult

open class BaseViewModel : ViewModel() {

    val errorApiResult = MutableSharedFlow<ErrorApiResult<Any>>()
    val progressBarState = MutableSharedFlow<Boolean>()
}