package space.pixelsg.urlgallery.common.mvvm

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import space.pixelsg.urlgallery.App
import java.util.logging.Logger

abstract class BaseActivity<AVM : BaseViewModel> : AppCompatActivity() {
    val app by lazy { application as App }
    val logger = Logger.getLogger(this::class.simpleName)

    protected val viewModel by lazy { provideViewModel() as AVM }

    protected fun requireArguments() = intent.extras ?: bundleOf()

    open fun provideViewModel() = ViewModelProvider(
        viewModelStore,
        BaseFactory(
            app,
        )
    )[BaseViewModel::class.java]
}