package com.gurman.myapplication.feature.select

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.DoNotInline
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp

@Composable
fun PopUpMenu2() {
    val clipboardManager = LocalClipboardManager.current

    val annotated = AnnotatedString(
        text = textForScroll,
//        spanStyles = listOf(Range(SpanStyle(fontWeight = FontWeight.W600, color = Color.Blue), 0, 10)),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val view = LocalView.current
        val context = LocalContext.current
        val textToolbar = remember {
            AndroidTextToolbar(
                view = view,
                callback = object : TextToolbarMenu {
                    override val onCopyRequested: () -> Unit = {
                        val item = clipboardManager.getText()
                        val highlighted = item?.text ?: ""
                        val start = textForScroll.indexOf(highlighted)
                        val end = highlighted.length + start

                        annotated.getStringAnnotations(highlighted, 10, 20)

                        Toast.makeText(context, "Copy4 start=${start}, end=$end $highlighted", Toast.LENGTH_LONG).show()
                    }
                }
            )
        }


        CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
            SelectionContainer {
                Text(text = annotated)
            }
        }
    }
}

interface TextToolbarMenu {
    val onCopyRequested: (() -> Unit)?
//    val onPasteRequested: (() -> Unit)?
//    val onCutRequested: (() -> Unit)?
//    val onSelectAllRequested: (() -> Unit)?
}


internal class AndroidTextToolbar(private val view: View, val callback: TextToolbarMenu? = null) : TextToolbar {
    private var actionMode: ActionMode? = null
    private val textActionModeCallback: TextActionModeCallback = TextActionModeCallback(
        onActionModeDestroy = { actionMode = null }
    )
    override var status: TextToolbarStatus = TextToolbarStatus.Hidden
        //        private set

    override fun showMenu(
        rect: Rect,
        onCopyRequested: (() -> Unit)?,
        onPasteRequested: (() -> Unit)?,
        onCutRequested: (() -> Unit)?,
        onSelectAllRequested: (() -> Unit)?
    ) {
        if (status == TextToolbarStatus.Shown) {
            textActionModeCallback.rect = rect
            textActionModeCallback.onCopyRequested = {
                onCopyRequested?.invoke()
                callback?.onCopyRequested?.invoke()
            }
            textActionModeCallback.onCutRequested = onCutRequested
            textActionModeCallback.onPasteRequested = onPasteRequested
            textActionModeCallback.onSelectAllRequested = onSelectAllRequested


            if (actionMode == null) {
//            status = TextToolbarStatus.Shown
                actionMode = TextToolbarHelperMethods.startActionMode(
                    view,
                    FloatingTextActionModeCallback(textActionModeCallback),
                    ActionMode.TYPE_FLOATING
                )
            } else {
                actionMode?.invalidate()
            }
        }
    }

    override fun hide() {
        if (status == TextToolbarStatus.Hidden) {
//            status = TextToolbarStatus.Hidden
            actionMode?.finish()
            actionMode = null
        }
    }
}

/**
 * This class is here to ensure that the classes that use this API will get verified and can be
 * AOT compiled. It is expected that this class will soft-fail verification, but the classes
 * which use this method will pass.
 */
internal object TextToolbarHelperMethods {
    @DoNotInline
    fun startActionMode(
        view: View,
        actionModeCallback: ActionMode.Callback,
        type: Int
    ): ActionMode? {
        return view.startActionMode(
            actionModeCallback,
            type
        )
    }

    @DoNotInline
    fun invalidateContentRect(actionMode: ActionMode) {
        actionMode.invalidateContentRect()
    }
}


internal class TextActionModeCallback(
    val onActionModeDestroy: (() -> Unit)? = null,
    var rect: Rect = Rect.Zero,
    var onCopyRequested: (() -> Unit)? = null,
    var onPasteRequested: (() -> Unit)? = null,
    var onCutRequested: (() -> Unit)? = null,
    var onSelectAllRequested: (() -> Unit)? = null
) {
    fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        requireNotNull(menu) { "onCreateActionMode requires a non-null menu" }
        requireNotNull(mode) { "onCreateActionMode requires a non-null mode" }

        onCopyRequested?.let {
            addMenuItem(menu, MenuItemOption.Copy)
        }
        onPasteRequested?.let {
            addMenuItem(menu, MenuItemOption.Paste)
        }
        onCutRequested?.let {
            addMenuItem(menu, MenuItemOption.Cut)
        }
        onSelectAllRequested?.let {
            addMenuItem(menu, MenuItemOption.SelectAll)
        }
        return true
    }

    // this method is called to populate new menu items when the actionMode was invalidated
    fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        if (mode == null || menu == null) return false
        updateMenuItems(menu)
        // should return true so that new menu items are populated
        return true
    }

    fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        when (item!!.itemId) {
            MenuItemOption.Copy.id -> onCopyRequested?.invoke()
            MenuItemOption.Paste.id -> onPasteRequested?.invoke()
            MenuItemOption.Cut.id -> onCutRequested?.invoke()
            MenuItemOption.SelectAll.id -> onSelectAllRequested?.invoke()
            else -> return false
        }
        mode?.finish()
        return true
    }

    fun onDestroyActionMode() {
        onActionModeDestroy?.invoke()
    }

    @VisibleForTesting
    internal fun updateMenuItems(menu: Menu) {
        addOrRemoveMenuItem(menu, MenuItemOption.Copy, onCopyRequested)
        addOrRemoveMenuItem(menu, MenuItemOption.Paste, onPasteRequested)
        addOrRemoveMenuItem(menu, MenuItemOption.Cut, onCutRequested)
        addOrRemoveMenuItem(menu, MenuItemOption.SelectAll, onSelectAllRequested)
    }

    internal fun addMenuItem(menu: Menu, item: MenuItemOption) {
        menu.add(0, item.id, item.order, item.titleResource)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    private fun addOrRemoveMenuItem(
        menu: Menu,
        item: MenuItemOption,
        callback: (() -> Unit)?
    ) {
        when {
            callback != null && menu.findItem(item.id) == null -> addMenuItem(menu, item)
            callback == null && menu.findItem(item.id) != null -> menu.removeItem(item.id)
        }
    }
}

internal enum class MenuItemOption(val id: Int) {
    Copy(0),
    Paste(1),
    Cut(2),
    SelectAll(3);

    val titleResource: Int
        get() = when (this) {
            Copy -> android.R.string.copy
            Paste -> android.R.string.paste
            Cut -> android.R.string.cut
            SelectAll -> android.R.string.selectAll
        }

    /**
     * This item will be shown before all items that have order greater than this value.
     */
    val order = id
}

internal class FloatingTextActionModeCallback(
    private val callback: TextActionModeCallback
) : ActionMode.Callback2() {
    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return callback.onActionItemClicked(mode, item)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onCreateActionMode(mode, menu)
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return callback.onPrepareActionMode(mode, menu)
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        callback.onDestroyActionMode()
    }

    override fun onGetContentRect(
        mode: ActionMode?,
        view: View?,
        outRect: android.graphics.Rect?
    ) {
        val rect = callback.rect
        outRect?.set(
            rect.left.toInt(),
            rect.top.toInt(),
            rect.right.toInt(),
            rect.bottom.toInt()
        )
    }
}