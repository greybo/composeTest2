package com.gurman.myapplication.feature.fullscreen

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.view.ContextThemeWrapper
import android.view.View
import android.view.ViewOutlineProvider
import android.view.Window
import androidx.activity.ComponentDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.gurman.myapplication.R
import java.util.UUID

@ExperimentalMaterial3Api
@Composable
fun FullSizeLayout(content: @Composable () -> Unit) {
    val view = LocalView.current
    val density = LocalDensity.current
    val composition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val dialogId = rememberSaveable { UUID.randomUUID() }
    val dialog =
        remember(view, density) {
            ModalBottomSheetDialogWrapper(
                view,
                density,
                dialogId,
            ).apply {
                setContent(composition) {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        currentContent()
                    }
                }
            }
        }

    DisposableEffect(dialog) {
        dialog.show()

        onDispose {
            dialog.dismiss()
            dialog.disposeComposition()
        }
    }
}


@SuppressLint("PrivateResource")
@OptIn(ExperimentalMaterial3Api::class)
private class ModalBottomSheetDialogWrapper(
    private val composeView: View,
    density: Density,
    dialogId: UUID,
) : ComponentDialog(
    ContextThemeWrapper(
        composeView.context,
        androidx.compose.material3.R.style.EdgeToEdgeFloatingDialogWindowTheme
    )
), ViewRootForInspector {

    private val dialogLayout: ModalBottomSheetDialogLayout

    // On systems older than Android S, there is a bug in the surface insets matrix math used by
    // elevation, so high values of maxSupportedElevation break accessibility services: b/232788477.
    private val maxSupportedElevation = 8.dp

    override val subCompositionView: AbstractComposeView
        get() = dialogLayout

    init {
        val window = window ?: error("Dialog has no window")
        window.requestFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        dialogLayout =
            ModalBottomSheetDialogLayout(
                context,
                window,
            ).apply {
                // Set unique id for AbstractComposeView. This allows state restoration for the
                // state
                // defined inside the Dialog via rememberSaveable()
                setTag(R.id.compose_view_saveable_id_tag, "Dialog:$dialogId")
                // Enable children to draw their shadow by not clipping them
                clipChildren = false
                // Allocate space for elevation
                with(density) { elevation = maxSupportedElevation.toPx() }
                // Simple outline to force window manager to allocate space for shadow.
                // Note that the outline affects clickable area for the dismiss listener. In
                // case of
                // shapes like circle the area for dismiss might be to small (rectangular
                // outline
                // consuming clicks outside of the circle).
                outlineProvider =
                    object : ViewOutlineProvider() {
                        override fun getOutline(view: View, result: Outline) {
                            result.setRect(0, 0, view.width, view.height)
                            // We set alpha to 0 to hide the view's shadow and let the
                            // composable to draw
                            // its own shadow. This still enables us to get the extra space
                            // needed in the
                            // surface.
                            result.alpha = 0f
                        }
                    }
            }
        // Clipping logic removed because we are spanning edge to edge.

        setContentView(dialogLayout)
        dialogLayout.setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        dialogLayout.setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        dialogLayout.setViewTreeSavedStateRegistryOwner(
            composeView.findViewTreeSavedStateRegistryOwner()
        )
    }

    fun setContent(parentComposition: CompositionContext, children: @Composable () -> Unit) {
        dialogLayout.setContent(parentComposition, children)
    }

    fun disposeComposition() {
        dialogLayout.disposeComposition()
    }

    override fun cancel() {
        // Prevents the dialog from dismissing itself
        return
    }
}

@Suppress("ViewConstructor")
private class ModalBottomSheetDialogLayout(
    context: Context,
    override val window: Window,
) : AbstractComposeView(context), DialogWindowProvider {

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    fun setContent(parent: CompositionContext, content: @Composable () -> Unit) {
        setParentCompositionContext(parent)
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
        createComposition()
    }

    @Composable
    override fun Content() {
        content()
    }
}

