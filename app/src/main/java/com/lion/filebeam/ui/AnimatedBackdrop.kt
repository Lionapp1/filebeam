package com.lion.filebeam.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Temaya (açık/koyu) duyarlı, hafif hareketli degrade arka plan. Düşük alfa değerleri
 * ile metin okunabilirliği korunur; tek sonsuz animasyon kullanılarak performans
 * etkilenmez. Tüm ekranlarda Scaffold'un containerColor'ı Color.Transparent yapılarak
 * bu arka plan görünür hale getirilir.
 */
@Composable
fun AnimatedBackdrop(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val isDark = MaterialTheme.colorScheme.onSurface.luminance() > 0.5f
    val transition = rememberInfiniteTransition(label = "backdrop")
    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 11000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "backdrop-shift",
    )
    val primary = if (isDark) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
        else MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
    val secondary = if (isDark) MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
        else MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)
    val tertiary = if (isDark) MaterialTheme.colorScheme.tertiary.copy(alpha = 0.10f)
        else MaterialTheme.colorScheme.tertiary.copy(alpha = 0.06f)
    val base = if (isDark) Color(0xFF0A0E16) else Color(0xFFF5F7FC)
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = base)
                val w = size.width
                val h = size.height
                val x = w * (0.18f + 0.62f * shift)
                val y = h * (0.12f + 0.72f * (1f - shift))
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(primary, Color.Transparent),
                        center = Offset(x, y),
                        radius = w * 0.75f,
                    ),
                )
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(secondary, Color.Transparent),
                        center = Offset(w - x * 0.55f, h - y * 0.45f),
                        radius = w * 0.65f,
                    ),
                )
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(tertiary, Color.Transparent),
                        center = Offset(w * 0.5f, h * 0.5f),
                        radius = w * 0.95f,
                    ),
                )
            },
    ) {
        content()
    }
}

private fun Color.luminance(): Float {
    // Basit bir parlaklık tahmini; tema tespiti için yeterli.
    return (0.299f * red + 0.587f * green + 0.114f * blue)
}
