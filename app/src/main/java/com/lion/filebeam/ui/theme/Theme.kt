package com.lion.filebeam.ui.theme

import android.content.Context
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Modern, consistent brand palette shared across phone and Android TV.
private val BeamBlue = Color(0xFF246BCE)
private val BeamCyan = Color(0xFF61D8E8)
private val BeamIndigo = Color(0xFF5567E8)

private val FileBeamShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(36.dp),
)

private val LightColors = lightColorScheme(
    primary = BeamBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE1EBFF),
    onPrimaryContainer = Color(0xFF102B55),
    secondary = Color(0xFF147E73),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD7F3ED),
    onSecondaryContainer = Color(0xFF123E39),
    tertiary = BeamIndigo,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE5E7FF),
    onTertiaryContainer = Color(0xFF252D70),
    background = Color(0xFFF4F7FC),
    onBackground = Color(0xFF161B24),
    surface = Color(0xFFFAFCFF),
    onSurface = Color(0xFF161B24),
    surfaceVariant = Color(0xFFE7ECF5),
    onSurfaceVariant = Color(0xFF4A5668),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF7F9FD),
    surfaceContainer = Color(0xFFF1F4FA),
    surfaceContainerHigh = Color(0xFFECEFF6),
    outline = Color(0xFF738198),
    outlineVariant = Color(0xFFD1D8E4),
)

private val DarkColors = darkColorScheme(
    primary = BeamCyan,
    onPrimary = Color(0xFF003740),
    primaryContainer = Color(0xFF17566B),
    onPrimaryContainer = Color(0xFFD4F7FF),
    secondary = Color(0xFF74D8C8),
    onSecondary = Color(0xFF003731),
    secondaryContainer = Color(0xFF1B514B),
    onSecondaryContainer = Color(0xFFD1F7EF),
    tertiary = Color(0xFFAEB5FF),
    onTertiary = Color(0xFF28317A),
    tertiaryContainer = Color(0xFF3F477F),
    onTertiaryContainer = Color(0xFFE3E4FF),
    background = Color(0xFF090B10),
    onBackground = Color(0xFFEDF1F7),
    surface = Color(0xFF0D1017),
    onSurface = Color(0xFFEDF1F7),
    surfaceVariant = Color(0xFF252C38),
    onSurfaceVariant = Color(0xFFC2C9D7),
    surfaceContainerLowest = Color(0xFF06070A),
    surfaceContainerLow = Color(0xFF11151D),
    surfaceContainer = Color(0xFF171C25),
    surfaceContainerHigh = Color(0xFF1D2330),
    outline = Color(0xFF8995A7),
    outlineVariant = Color(0xFF343C49),
)

@Composable
fun FileBeamTheme(
    darkTheme: Boolean = androidx.compose.foundation.isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    context: Context = LocalContext.current,
    content: @Composable () -> Unit,
) {
    val scheme = when {
        dynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S ->
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(colorScheme = scheme, shapes = FileBeamShapes, typography = Typography(), content = content)
}
