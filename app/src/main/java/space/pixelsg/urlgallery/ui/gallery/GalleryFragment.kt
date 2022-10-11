package space.pixelsg.urlgallery.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import space.pixelsg.urlgallery.R
import space.pixelsg.urlgallery.common.mvvm.BaseFragment
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel
import space.pixelsg.urlgallery.databinding.GalleryFragmentBinding
import space.pixelsg.urlgallery.ui.UiUtils.repeatOnStarted
import space.pixelsg.urlgallery.ui.file.FileActivity
import space.pixelsg.urlgallery.ui.gallery.adapter.CustomSpanGridLayoutManager
import space.pixelsg.urlgallery.ui.gallery.adapter.GalleryPagingAdapter

class GalleryFragment : BaseFragment<BaseViewModel, GalleryViewModel>() {
    private lateinit var binding: GalleryFragmentBinding
    private val adapter by lazy {
        GalleryPagingAdapter {
            findNavController().navigate(
                R.id.action_galleryFragment_to_fileActivity,
                bundleOf(
                    FileActivity.FILE_ID to it.id,
                    FileActivity.FILE_NAME to it.name,
                    FileActivity.FILE_URI to it.uri
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GalleryFragmentBinding.inflate(inflater, container, false)

        binding.toolbar.title = requireArguments().getString(BUCKET_NAME)
        binding.toolbar.subtitle = getString(
            R.string.items_count,
            requireArguments().getInt(BUCKET_ITEMS_COUNT, 0)
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = CustomSpanGridLayoutManager(context, 4) {
            adapter.isHeader(it)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        repeatOnStarted {
            viewModel.pagedGallery.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun provideViewModel() = ViewModelProvider(
        viewModelStore,
        GalleryViewModel.Factory(
            app,
            requireArguments().getLong(BUCKET_ID)
        )
    )[GalleryViewModel::class.java]

    companion object {
        const val BUCKET_ID = "bucket-id"
        const val BUCKET_NAME = "bucket-name"
        const val BUCKET_ITEMS_COUNT = "bucket-items-count"
    }
}