package io.github.takusan23.akaridroid.ui.screen.about

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import io.github.takusan23.akaridroid.R
import io.github.takusan23.akaridroid.tool.PersianDate
import io.github.takusan23.akaridroid.ui.screen.NavigationPaths
import kotlinx.coroutines.delay

// این interface برای سازگاری با AboutComponent.kt نگه داشته شده
sealed interface AboutScreenUiState {
    data object Init : AboutScreenUiState
    data object RouteSelect : AboutScreenUiState
    enum class AdvScenario(val titleResId: Int) {
        Sushi(R.string.setting_about_sushi),
        GitHub(R.string.setting_about_open_github),
        Library(R.string.setting_about_library)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onNavigate: (NavigationPaths) -> Unit = {}
) {
    val context = LocalContext.current
    var now by remember { mutableStateOf(PersianDate.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = PersianDate.now()
            delay(30_000)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("درباره ما", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_outline_arrow_back_24px),
                            contentDescription = "بازگشت"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A1A2E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFF0B0B0F)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // کارت تاریخ و ساعت شمسی
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF15151F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = PersianDate.today(),
                        color = Color(0xFFFF8A00),
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = now,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // کارت معرفی
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF15151F)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text("ردلاین فریم", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Spacer(Modifier.height(16.dp))
                    InfoRow("توسعه‌دهنده", "تیم نرم‌افزاری ردلاین سافت البرز")
                    InfoRow("مدیریت", "مهندس مهدی رضایی")
                    InfoRow("نسخه", "1.0.0")
                    Spacer(Modifier.height(20.dp))
                    Text("توضیحات برنامه", color = Color(0xFFFF2E63), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "ردلاین فریم؛ یک ویرایشگر ویدیوی حرفه‌ای، سبک و کاملاً آفلاین برای اندروید است. این برنامه با تمرکز بر سرعت، دقت و سادگی طراحی شده تا بتوانید در چند دقیقه ویدیوهای خود را برای اینستاگرام، یوتیوب، تیک‌تاک و سایر شبکه‌های اجتماعی آماده کنید.\n\n✦ امکانات کلیدی:\n• ویرایش تایم‌لاین چندلایه\n• پشتیبانی از ویدیوهای HDR و 4K\n• خروجی بدون واترمارک\n• بدون نیاز به اینترنت و بدون آپلود داده\n\n✦ چرا ردلاین فریم؟\nتمام پردازش‌ها روی دستگاه شما انجام می‌شود؛ یعنی حریم خصوصی شما کاملاً محفوظ می‌ماند.",
                        color = Color(0xFFCCCCD8),
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Justify
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            GradientButton(text = "⭐  ثبت نظر در مایکت") {
                try {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, "myket://comment?id=red.line.frame".toUri())
                    )
                } catch (e: Exception) {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW, "https://myket.ir/app/red.line.frame".toUri())
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            GradientButton(text = "📱  دیگر برنامه‌های ما") {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, "https://myket.ir/developer/dev-36089".toUri())
                )
            }

            Spacer(Modifier.height(12.dp))

            GradientButton(text = "✉️  تماس با پشتیبانی") {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("gmnarcis@gmail.com"))
                    putExtra(Intent.EXTRA_SUBJECT, "پشتیبانی ردلاین فریم")
                }
                context.startActivity(intent)
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color(0xFF808090), fontSize = 13.sp)
        Text(text = value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun GradientButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFF2E63), Color(0xFFFF8A00))
                ),
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}