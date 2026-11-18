package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
  primary = EmeraldPrimary,
  onPrimary = Color.White,
  primaryContainer = EmeraldContainer,
  onPrimaryContainer = EmeraldContainerText,
  secondary = AmberStreak,
  onSecondary = Color.White,
  secondaryContainer = AmberContainer,
  onSecondaryContainer = AmberContainerText,
  tertiary = CyanAccent,
  onTertiary = Color.White,
  tertiaryContainer = CyanContainer,
  onTertiaryContainer = CyanContainerText,
  error = RubyError,
  onError = Color.White,
  errorContainer = RubyContainer,
  onErrorContainer = RubyContainerText,
  background = CanvasBackground,
  onBackground = TextSlateHeadline,
  surface = SurfaceCard,
  onSurface = TextSlateHeadline,
  surfaceVariant = SlotBackground,
  onSurfaceVariant = TextSlateSubdued,
  outline = BorderSlate,
  outlineVariant = BorderSlateBase
)

@Composable
fun VividLingoTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colorScheme = LightColorScheme,
    typography = AppTypography,
    content = content
  )
}
