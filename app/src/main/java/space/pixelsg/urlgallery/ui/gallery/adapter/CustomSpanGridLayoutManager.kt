package space.pixelsg.urlgallery.ui.gallery.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CustomSpanGridLayoutManager(
    context: Context?,
    private val spans: Int,
    private val isHeader: (Int) -> Boolean = { false }
) : GridLayoutManager(context, spans) {
    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int) =
                if (isHeader(position)) spans
                else 1
        }
    }
}