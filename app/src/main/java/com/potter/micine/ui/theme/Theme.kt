
package com.potter.micine.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// TEMA ROJO CLARO
private val LightColorScheme = lightColorScheme(
    primary = RojoPrimarioClaro,
    onPrimary = SobreRojoClaro,
    primaryContainer = RojoContenedorClaro,
    onPrimaryContainer = SobreContenedorClaro,

    secondary = RojoSecundarioClaro,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFDBD6),
    onSecondaryContainer = Color(0xFF3D211F),

    tertiary = Color(0xFF9F3B48),
    onTertiary = Color.White,

    background = FondoAppClaro,
    onBackground = TextoAppClaro,

    surface = FondoAppClaro,
    onSurface = TextoAppClaro,

    surfaceVariant = TarjetaAppClara,
    onSurfaceVariant = TextoSecundarioClaro,

    surfaceContainerLow = Color(0xFFFFF2F0),
    surfaceContainer = BarraAppClara,
    surfaceContainerHigh = TarjetaAppClara,
    surfaceContainerHighest = Color(0xFFF3D7D4),

    outline = BordeAppClaro
)

// TEMA ROJO OSCURO
private val DarkColorScheme = darkColorScheme(
    primary = RojoPrimarioOscuro,
    onPrimary = SobreRojoOscuro,
    primaryContainer = RojoContenedorOscuro,
    onPrimaryContainer = SobreContenedorOscuro,

    secondary = RojoSecundarioOscuro,
    onSecondary = Color(0xFF432825),
    secondaryContainer = Color(0xFF5A3834),
    onSecondaryContainer = Color(0xFFFFDAD4),

    tertiary = Color(0xFFFFB2BE),
    onTertiary = Color(0xFF5E192A),

    background = FondoAppOscuro,
    onBackground = TextoAppOscuro,

    surface = FondoAppOscuro,
    onSurface = TextoAppOscuro,

    surfaceVariant = TarjetaAppOscura,
    onSurfaceVariant = TextoSecundarioOscuro,

    surfaceContainerLow = Color(0xFF211719),
    surfaceContainer = BarraAppOscura,
    surfaceContainerHigh = TarjetaAppOscura,
    surfaceContainerHighest = Color(0xFF3E2C2F),

    outline = BordeAppOscuro
)

@Composable
fun MICINETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colores = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colores,
        typography = Typography,
        content = content
    )
}
