package space.pixelsg.urlgallery.common.mvvm

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import space.pixelsg.urlgallery.App
import java.util.logging.Logger

open class BaseViewModel(private val app: App) : AndroidViewModel(app) {
    val graph by lazy { app.graph }
    val logger = Logger.getLogger(this::class.simpleName)

    protected fun <T> Flow<T>.shareWhileSubscribed(timeout: Long = Long.MAX_VALUE) =
        this.shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(
                stopTimeoutMillis = timeout
            ), 1
        )
}