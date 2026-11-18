package com.lion.filebeam.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Hafif hareketli bir degrade arka plan. Tüm ekranlarda kullanılabilir; performansı
 * etkilememesi için tek bir sonsuz animasyon ve hafif alfa değerleri kullanır.
 */
@Composable
fun AnimatedBackdrop(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val transition = rememberInfiniteTransition(label = "backdrop")
    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 9000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "backdrop-shift",
    )
    val primary = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
    val secondary = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f)
    val tertiary = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.06f)
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                val w = size.width
                val h = size.height
                val x = w * (0.2f + 0.6f * shift)
                val y = h * (0.15f + 0.7f * (1f - shift))
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(primary, Color.Transparent),
                        center = Offset(x, y),
                        radius = w * 0.7f,
                    ),
                )
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(secondary, Color.Transparent),
                        center = Offset(w - x * 0.6f, h - y * 0.5f),
                        radius = w * 0.6f,
                    ),
                )
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(tertiary, Color.Transparent),
                        center = Offset(w * 0.5f, h * 0.5f),
                        radius = w * 0.9f,
                    ),
                )
            },
    ) {
        content()
    }
}