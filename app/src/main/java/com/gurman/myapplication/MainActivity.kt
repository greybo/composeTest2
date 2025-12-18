package com.gurman.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.gurman.myapplication.feature.pager.TabsIndicatorLine4
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper

//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        }
//        val decorView = window.decorView
//        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//        window.decorView.requestFitSystemWindows()
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
//            window.statusBarColor = Color.TRANSPARENT
//        }
        setContent {
            TestAppThemeWrapper {
                Navigator(
                    screen = MainScreen(),
//                    screen = PickerDateScreen(),
                ) { navigator ->
                    SlideTransition(navigator)
                }
            }
        }

//        setContent {
//            TestAppThemeWrapper {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(innerPadding)
//                }
//            }
//        }
    }
    fun setStatusBarTransparent(activity: Activity, view: View) {
        activity.apply {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            ViewCompat.setOnApplyWindowInsetsListener(view) { root, windowInset ->
                val inset = windowInset.getInsets(WindowInsetsCompat.Type.systemBars())
                root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                    leftMargin = inset.left
                    bottomMargin = inset.bottom
                    rightMargin = inset.right
                    topMargin = 0
                }
                WindowInsetsCompat.CONSUMED
            }
        }
    }
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}

@Composable
fun Greeting(innerPadding: PaddingValues) {
//    ImageCoilExample()
//    DeviceMedia(listOf(MediaStoreData()))
//    RadioButtonSingleSelection()
//    WithDialogPickerScreen()
//    DrawCanvasComponent()
//    DrawImageExample()

//    MainSelectedTextComponent()
//    BaseTextComponent()
//    ClickableTextComponent()
//    MainSelectedTextComponent()
//    PopUpMenu2()
//    PopUpMenuComponent()
//    SelectableTextComponent()
//    CenterAlignedTopAppBarExample()
//    WithDialogPickerScreen()
//    PagerTest2()
//    PagerTest3()
    TabsIndicatorLine4()
//    AutoAdvancePager()
//    BasicTooltip { SimpleContent(it) }
//    SwipeToDismissLayout()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestAppThemeWrapper {
        Greeting(PaddingValues())
    }
}