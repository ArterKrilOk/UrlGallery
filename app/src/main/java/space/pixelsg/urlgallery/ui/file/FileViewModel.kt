package space.pixelsg.urlgallery.ui.file

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import space.pixelsg.urlgallery.App
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel
import space.pixelsg.urlgallery.ui.file.models.SharableFile

class FileViewModel(
    private val app: App,
    private val fileID: Long,
    private val fileName: String,
    private val fileUri: Uri
) : BaseViewModel(app) {
    private val BASE_URL = "https://data.pixelsg.space/file/?file="
    private val url = MutableStateFlow<String?>(null)

    val urlFlow = graph.sharedLinksProvider
        .getSharedLink(fileID)
        .shareWhileSubscribed()

    val shareFile = combine(
        flowOf(
            SharableFile(
                fileID, fileUri, fileName, null
            )
        ),
        urlFlow
    ) { file, newUrl ->
        file.copy(link = newUrl?.link)
    }.shareWhileSubscribed()


    @OptIn(FlowPreview::class)
    fun uploadImage() = shareFile.flatMapConcat {
        graph.networkProvider.uploadFileFlow(it.uri, it.name, app.contentResolver)
    }.map {
        graph.sharedLinksProvider.addSharedLink(
            fileID,
            BASE_URL + it.first().serverFileID
        )
    }

    class Factory(
        private val app: App,
        private val fileID: Long,
        private val fileName: String,
        private val fileUri: Uri
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass
                .getConstructor(
                    App::class.java,
                    Long::class.java,
                    String::class.java,
                    Uri::class.java
                )
                .newInstance(
                    app,
                    fileID,
                    fileName,
                    fileUri
                )
        }
    }
}