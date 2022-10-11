package space.pixelsg.urlgallery.ui.file

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import space.pixelsg.urlgallery.R
import space.pixelsg.urlgallery.common.mvvm.BaseActivity
import space.pixelsg.urlgallery.databinding.ActivityFileBinding
import space.pixelsg.urlgallery.ui.UiUtils.repeatOnStarted
import space.pixelsg.urlgallery.ui.file.models.SharableFile


class FileActivity : BaseActivity<FileViewModel>() {
    private lateinit var binding: ActivityFileBinding

    private val clipboard by lazy {
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        binding.btnGenerateLink.setOnClickListener {
            GlobalScope.launch {
                viewModel.uploadImage().onStart {
                    runOnUiThread {
                        binding.progressCircular.isVisible = true
                        binding.imageView.foreground = ColorDrawable(getColor(R.color.mask))
                    }
                }.catch {
                    it.printStackTrace()
                }.collect {
                    runOnUiThread {
                        binding.imageView.foreground = null
                        binding.progressCircular.isVisible = false
                    }
                }
            }
        }
        repeatOnStarted {
            viewModel.shareFile.collect(this@FileActivity::setShareFile)
        }
    }

    private fun setShareFile(file: SharableFile) {
        Glide
            .with(binding.imageView)
            .load(file.uri)
            .into(binding.imageView)
        binding.linkControls.isVisible = file.link != null
        binding.btnGenerateLink.isVisible = file.link == null
        binding.btnCopy.setOnClickListener {
            clipboard.setPrimaryClip(ClipData.newPlainText(file.name, file.link))
        }
        binding.btnShare.setOnClickListener {
            ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle("Share link to ${file.name}")
                .setText(file.link)
                .startChooser()
        }
    }

    override fun provideViewModel() = ViewModelProvider(
        viewModelStore,
        FileViewModel.Factory(
            app,
            requireArguments().getLong(FILE_ID),
            requireArguments().getString(FILE_NAME) ?: "",
            requireArguments().getParcelable(FILE_URI) ?: Uri.EMPTY
        )
    )[FileViewModel::class.java]

    companion object {
        const val FILE_NAME = "file-name"
        const val FILE_URI = "file-uri"
        const val FILE_ID = "file-id"
    }
}