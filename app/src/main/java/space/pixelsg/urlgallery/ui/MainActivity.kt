package space.pixelsg.urlgallery.ui

import android.os.Bundle
import space.pixelsg.urlgallery.R
import space.pixelsg.urlgallery.common.mvvm.BaseActivity
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel

class MainActivity : BaseActivity<BaseViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}