package space.pixelsg.urlgallery.ui.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import space.pixelsg.urlgallery.R
import space.pixelsg.urlgallery.common.mvvm.BaseFactory
import space.pixelsg.urlgallery.common.mvvm.BaseFragment
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel
import space.pixelsg.urlgallery.databinding.AlbumsFragmentBinding
import space.pixelsg.urlgallery.ui.UiUtils.repeatOnStarted
import space.pixelsg.urlgallery.ui.albums.adapter.AlbumViewAdapter
import space.pixelsg.urlgallery.ui.gallery.GalleryFragment

class AlbumsFragment : BaseFragment<BaseViewModel, AlbumsViewModel>() {

    private lateinit var binding: AlbumsFragmentBinding

    private val adapter by lazy {
        AlbumViewAdapter {
            findNavController().navigate(
                R.id.action_albumsFragment_to_galleryFragment,
                bundleOf(
                    GalleryFragment.BUCKET_ID to it.id,
                    GalleryFragment.BUCKET_NAME to it.name,
                    GalleryFragment.BUCKET_ITEMS_COUNT to it.items
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlbumsFragmentBinding.inflate(inflater, container, false)

        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repeatOnStarted {
            viewModel.albums.collect {
                adapter.items = it
            }
        }
    }

    override fun provideViewModel() = ViewModelProvider(
        viewModelStore,
        BaseFactory(app)
    )[AlbumsViewModel::class.java]
}