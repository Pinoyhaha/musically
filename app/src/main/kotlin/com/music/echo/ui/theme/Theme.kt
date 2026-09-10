package echo.music.iad1tya.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.platform.LocalContext
import androidx.palette.graphics.Palette
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.rememberDynamicColorScheme
import com.materialkolor.score.Score

val DefaultThemeColor = Color(0xFFFF375F)

/**
 * Main Musically theme. The player is deliberately left untouched; this only
 * changes the global Material surfaces, typography shape, and app chrome.
 */
@Composable
fun echomusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    pureBlack: Boolean = false,
    themeColor: Color = DefaultThemeColor,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val baseColorScheme = rememberDynamicColorScheme(
        seedColor = themeColor,
        isDark = darkTheme,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
        style = PaletteStyle.TonalSpot
    )

    val colorScheme = remember(baseColorScheme, pureBlack, darkTheme, themeColor) {
        if (darkTheme) {
            baseColorScheme.copy(
                primary = themeColor,
                onPrimary = if (themeColor.luminance() > 0.45f) Color.Black else Color.White,
                background = if (pureBlack) Color.Black else Color(0xFF0B0B0D),
                surface = if (pureBlack) Color.Black else Color(0xFF101012),
                surfaceDim = if (pureBlack) Color.Black else Color(0xFF101012),
                surfaceBright = if (pureBlack) Color(0xFF17171A) else Color(0xFF1C1C20),
                surfaceContainerLowest = Color.Black,
                surfaceContainerLow = if (pureBlack) Color(0xFF050507) else Color(0xFF161619),
                surfaceContainer = if (pureBlack) Color(0xFF08080A) else Color(0xFF1A1A1E),
                surfaceContainerHigh = if (pureBlack) Color(0xFF0D0D10) else Color(0xFF202024),
                surfaceContainerHighest = if (pureBlack) Color(0xFF121216) else Color(0xFF26262B)
            )
        } else {
            baseColorScheme.copy(
                primary = themeColor,
                onPrimary = if (themeColor.luminance() > 0.45f) Color.Black else Color.White,
                background = Color(0xFFF7F7F9),
                surface = Color.White,
                surfaceDim = Color(0xFFE8E8EC),
                surfaceBright = Color.White,
                surfaceContainerLowest = Color.White,
                surfaceContainerLow = Color(0xFFF2F2F6),
                surfaceContainer = Color(0xFFEDEDF2),
                surfaceContainerHigh = Color(0xFFE7E7EC),
                surfaceContainerHighest = Color(0xFFE1E1E6)
            )
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = androidx.compose.material3.MaterialTheme.shapes.copy(
            extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
        ),
        content = content
    )
}

fun Bitmap.extractThemeColor(): Color {
    val colorsToPopulation = Palette.from(this)
        .maximumColorCount(8)
        .generate()
        .swatches
        .associate { it.rgb to it.population }
    val rankedColors = Score.score(colorsToPopulation)
    return Color(rankedColors.first())
}

fun Bitmap.extractGradientColors(): List<Color> {
    val extractedColors = Palette.from(this)
        .maximumColorCount(64)
        .generate()
        .swatches
        .associate { it.rgb to it.population }

    val orderedColors = Score.score(extractedColors, 2, 0xff4285f4.toInt(), true)
        .sortedByDescending { Color(it).luminance() }

    return if (orderedColors.size >= 2)
        listOf(Color(orderedColors[0]), Color(orderedColors[1]))
    else
        listOf(Color(0xFF595959), Color(0xFF0D0D0D))
}

fun ColorScheme.pureBlack(apply: Boolean) =
    if (apply) copy(
        surface = Color.Black,
        background = Color.Black,
        surfaceContainerLowest = Color.Black,
        surfaceContainerLow = Color(0xFF050507),
        surfaceContainer = Color(0xFF08080A)
    ) else this

val ColorSaver = object : Saver<Color, Int> {
    override fun restore(value: Int): Color = Color(value)
    override fun SaverScope.save(value: Color): Int = value.toArgb()
}
