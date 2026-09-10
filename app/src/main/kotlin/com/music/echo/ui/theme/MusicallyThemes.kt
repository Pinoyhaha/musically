package echo.music.iad1tya.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Built-in Musically palettes. These are intentionally seed colors so the
 * existing MaterialKolor engine can generate accessible light/dark schemes.
 * The music player is not affected by these presets.
 */
data class MusicallyThemePreset(
    val id: String,
    val name: String,
    val seed: Color,
    val description: String,
)

val MusicallyThemePresets = listOf(
    MusicallyThemePreset(
        id = "apple_music",
        name = "Apple Music",
        seed = Color(0xFFFF375F),
        description = "Clean, vibrant and glassy"
    ),
    MusicallyThemePreset(
        id = "spotify",
        name = "Spotify",
        seed = Color(0xFF1DB954),
        description = "Bold green music aesthetic"
    ),
    MusicallyThemePreset(
        id = "midnight",
        name = "Midnight",
        seed = Color(0xFF5B7CFF),
        description = "Deep blue night theme"
    ),
    MusicallyThemePreset(
        id = "purple_haze",
        name = "Purple Haze",
        seed = Color(0xFFA855F7),
        description = "Soft neon purple"
    ),
    MusicallyThemePreset(
        id = "ocean",
        name = "Ocean",
        seed = Color(0xFF06B6D4),
        description = "Cool cyan glass"
    ),
    MusicallyThemePreset(
        id = "rose",
        name = "Rose",
        seed = Color(0xFFFF6B8A),
        description = "Warm pink-red"
    )
)

fun findMusicallyThemePreset(color: Color): MusicallyThemePreset? =
    MusicallyThemePresets.firstOrNull { it.seed.toArgbSafe() == color.toArgbSafe() }

private fun Color.toArgbSafe(): Int =
    android.graphics.Color.argb(
        (alpha * 255f).toInt().coerceIn(0, 255),
        (red * 255f).toInt().coerceIn(0, 255),
        (green * 255f).toInt().coerceIn(0, 255),
        (blue * 255f).toInt().coerceIn(0, 255)
    )
