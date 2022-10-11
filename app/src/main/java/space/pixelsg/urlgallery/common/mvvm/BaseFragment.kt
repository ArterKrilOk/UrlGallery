package space.pixelsg.urlgallery.common.mvvm

import androidx.fragment.app.Fragment
import space.pixelsg.urlgallery.App
import java.util.logging.Logger

abstract class BaseFragment<AVM : BaseViewModel, FVM : BaseViewModel> : Fragment() {
    val app by lazy { activity?.application as App }
    val activityViewModel by lazy { (activity as BaseActivity<AVM>).provideViewModel() }
    val viewModel by lazy { provideViewModel() }
    val logger = Logger.getLogger(this::class.simpleName)

    abstract fun provideViewModel(): FVM
}