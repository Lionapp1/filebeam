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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** Refined, low-motion background used consistently across every screen. */
@Composable
fun AnimatedBackdrop(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val isDark = MaterialTheme.colorScheme.onSurface.luminance() > 0.5f
    val transition = rememberInfiniteTransition(label = "backdrop")
    val shift by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(14000, easing = LinearEasing), RepeatMode.Reverse),
        label = "backdropShift",
    )

    val base = if (isDark) Color(0xFF090B10) else Color(0xFFF4F7FC)
    val primary = if (isDark) MaterialTheme.colorScheme.primary.copy(alpha = 0.13f)
        else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    val secondary = if (isDark) MaterialTheme.colorScheme.secondary.copy(alpha = 0.10f)
        else MaterialTheme.colorScheme.secondary.copy(alpha = 0.06f)

    Box(modifier.fillMaxSize().drawBehind {
        drawRect(base)
        val w = size.width
        val h = size.height
        drawRect(Brush.radialGradient(
            listOf(primary, Color.Transparent),
            Offset(w * (0.12f + 0.76f * shift), h * (0.06f + 0.64f * (1f - shift))),
            w * 0.82f,
        ))
        drawRect(Brush.radialGradient(
            listOf(secondary, Color.Transparent),
            Offset(w * (0.88f - 0.30f * shift), h * (0.92f - 0.45f * shift)),
            w * 0.70f,
        ))
    }) { content() }
}

private fun Color.luminance(): Float = 0.299f * red + 0.587f * green + 0.114f * blue
