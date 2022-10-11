package space.pixelsg.urlgallery.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.runBlocking
import java.time.format.DateTimeFormatter

object UiUtils {
    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenStarted(block)
    }

    fun LifecycleOwner.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenCreated(block)
    }

    fun LifecycleOwner.repeatOnResumed(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launchWhenResumed(block)
    }

    @OptIn(FlowPreview::class)
    fun EditText.onTextChanged(delay: Long = 100) = callbackFlow {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                trySend(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                trySend(p0.toString())
            }
        }
        addTextChangedListener(watcher)
        send(text.toString())
        awaitClose { removeTextChangedListener(watcher) }
    }.debounce(delay).distinctUntilChanged()

    @OptIn(FlowPreview::class)
    fun View.onClick(delay: Long = 100) = callbackFlow {
        setOnClickListener {
            runBlocking {
                send(Unit)
            }
        }
        awaitClose { setOnClickListener(null) }
    }.debounce(delay)

    @OptIn(FlowPreview::class)
    fun SearchView.onTextChanged(delay: Long = 100) = callbackFlow {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                trySend(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trySend(newText ?: "")
                return true
            }

        })
        send(query.toString())
        awaitClose {
            setOnQueryTextListener(null)
        }
    }.debounce(delay).distinctUntilChanged()


    val HeaderDateFormatter: DateTimeFormatter by lazy {
        DateTimeFormatter.ofPattern("EEE, dd MMM")
    }
}