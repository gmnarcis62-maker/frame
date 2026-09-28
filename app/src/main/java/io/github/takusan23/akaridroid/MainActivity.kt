package io.github.takusan23.akaridroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import io.github.takusan23.akaridroid.ui.screen.AkariDroidMainScreen
import io.github.takusan23.akaridroid.ui.screen.SplashScreen
import io.github.takusan23.akaridroid.ui.theme.AkariDroidTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // راست‌چین کردن کل برنامه
        window.decorView.layoutDirection = android.view.View.LAYOUT_DIRECTION_RTL

        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                AkariDroidTheme {
                    AppRoot()
                }
            }
        }
    }
}

@Composable
private fun AppRoot() {
    var showSplash by remember { mutableStateOf(true) }
    var backPressedAt by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

    // اسپلش ۳ ثانیه‌ای
    LaunchedEffect(Unit) {
        delay(3000)
        showSplash = false
    }

    // خروج با دو بار فشار دکمه بازگشت
    BackHandler(enabled = !showSplash) {
        val now = System.currentTimeMillis()
        if (now - backPressedAt < 2000) {
            (context as? ComponentActivity)?.finish()
        } else {
            backPressedAt = now
            Toast.makeText(
                context,
                "برای خروج دوباره دکمه بازگشت را بزنید",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (showSplash) {
        SplashScreen(onFinished = { showSplash = false })
    } else {
        AkariDroidMainScreen()
    }
}