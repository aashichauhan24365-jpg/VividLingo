package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.BorderSlateBase
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanAccentBase
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldContainerText
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.EmeraldTinted
import com.example.ui.theme.GemBlue
import com.example.ui.theme.HeartRed
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.RubyContainer
import com.example.ui.theme.RubyContainerText
import com.example.ui.theme.RubyError
import com.example.ui.theme.RubyErrorBase
import com.example.ui.theme.SlotBackground
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued

enum class TactileButtonVariant {
  PRIMARY,      // Emerald Green
  SECONDARY,    // Amber Streak
  OUTLINE,      // White with Gray Border and Base
  CYAN,         // Bright Cyan
  DANGER        // Ruby Red
}

/**
 * 3D Tactile Push Button with physical extruded lip shadow.
 * Depresses by [lipHeight] when pressed, delivering authentic tactile feedback.
 */
@Composable
fun TactileButton(
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  text: String? = null,
  enabled: Boolean = true,
  variant: TactileButtonVariant = TactileButtonVariant.PRIMARY,
  lipHeight: Dp = 4.dp,
  shapeRadius: Dp = 9999.dp, // full pill contour by default
  contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
  testTag: String = "tactile_button",
  content: (@Composable () -> Unit)? = null
) {
  val haptic = LocalHapticFeedback.current
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  val faceColor = when (variant) {
    TactileButtonVariant.PRIMARY -> if (enabled) EmeraldPrimary else Color(0xFFE2E8F0)
    TactileButtonVariant.SECONDARY -> if (enabled) AmberStreak else Color(0xFFE2E8F0)
    TactileButtonVariant.OUTLINE -> SurfaceCard
    TactileButtonVariant.CYAN -> if (enabled) CyanAccent else Color(0xFFE2E8F0)
    TactileButtonVariant.DANGER -> if (enabled) RubyError else Color(0xFFE2E8F0)
  }

  val baseColor = when (variant) {
    TactileButtonVariant.PRIMARY -> if (enabled) EmeraldPrimaryBase else Color(0xFFCBD5E1)
    TactileButtonVariant.SECONDARY -> if (enabled) AmberStreakBase else Color(0xFFCBD5E1)
    TactileButtonVariant.OUTLINE -> BorderSlateBase
    TactileButtonVariant.CYAN -> if (enabled) CyanAccentBase else Color(0xFFCBD5E1)
    TactileButtonVariant.DANGER -> if (enabled) RubyErrorBase else Color(0xFFCBD5E1)
  }

  val textColor = when (variant) {
    TactileButtonVariant.PRIMARY,
    TactileButtonVariant.SECONDARY,
    TactileButtonVariant.CYAN,
    TactileButtonVariant.DANGER -> if (enabled) Color.White else TextSlateMuted
    TactileButtonVariant.OUTLINE -> if (enabled) TextSlateHeadline else TextSlateMuted
  }

  val cornerShape = RoundedCornerShape(shapeRadius)
  val currentOffset by animateDpAsState(
    targetValue = if (isPressed && enabled) lipHeight else 0.dp,
    animationSpec = spring(stiffness = 800f),
    label = "tactile_press"
  )

  Box(
    modifier = modifier
      .testTag(testTag)
      .height(54.dp + lipHeight)
      .clip(cornerShape)
      .background(baseColor, cornerShape)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
        onClick = {
          haptic.performHapticFeedback(HapticFeedbackType.LongPress)
          onClick()
        }
      )
  ) {
    // Face button surface
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(54.dp)
        .offset(y = currentOffset)
        .clip(cornerShape)
        .background(faceColor, cornerShape)
        .then(
          if (variant == TactileButtonVariant.OUTLINE) {
            Modifier.border(2.dp, BorderSlate, cornerShape)
          } else Modifier
        )
        .padding(contentPadding),
      contentAlignment = Alignment.Center
    ) {
      if (content != null) {
        content()
      } else if (text != null) {
        Text(
          text = text,
          color = textColor,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 16.sp,
          letterSpacing = 0.04.sp
        )
      }
    }
  }
}

/**
 * Tactile Card with 2px crisp border and ambient flat 3D base
 */
@Composable
fun TactileCard(
  modifier: Modifier = Modifier,
  shapeRadius: Dp = 20.dp,
  backgroundColor: Color = SurfaceCard,
  borderColor: Color = BorderSlate,
  lipColor: Color = BorderSlateBase,
  content: @Composable () -> Unit
) {
  val shape = RoundedCornerShape(shapeRadius)
  Box(
    modifier = modifier
      .clip(shape)
      .background(lipColor, shape)
      .padding(bottom = 3.dp)
      .clip(shape)
      .background(backgroundColor, shape)
      .border(2.dp, borderColor, shape)
  ) {
    content()
  }
}

/**
 * Tactile Selection Tile for multiple-choice and drill questions
 */
@Composable
fun TactileSelectableTile(
  text: String,
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  indexLabel: String? = null,
  isCorrect: Boolean? = null,
  isAudio: Boolean = false,
  onAudioClick: (() -> Unit)? = null
) {
  val haptic = LocalHapticFeedback.current
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  val shape = RoundedCornerShape(20.dp)

  val (bgColor, borderColor, baseColor, textColor) = when {
    isCorrect == true -> Quadruple(EmeraldContainer, EmeraldPrimary, EmeraldPrimaryBase, EmeraldContainerText)
    isCorrect == false -> Quadruple(RubyContainer, RubyError, RubyErrorBase, RubyContainerText)
    selected -> Quadruple(EmeraldTinted, EmeraldPrimary, EmeraldPrimaryBase, TextSlateHeadline)
    else -> Quadruple(SurfaceCard, BorderSlate, BorderSlateBase, TextSlateBody)
  }

  val currentOffset by animateDpAsState(
    targetValue = if (isPressed) 3.dp else 0.dp,
    animationSpec = spring(stiffness = 800f),
    label = "tile_press"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(68.dp)
      .clip(shape)
      .background(baseColor, shape)
      .clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = {
          haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
          onClick()
        }
      )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .offset(y = currentOffset)
        .clip(shape)
        .background(bgColor, shape)
        .border(2.dp, borderColor, shape)
        .padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        if (indexLabel != null) {
          Box(
            modifier = Modifier
              .size(30.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(if (selected) EmeraldPrimary else BorderSlate),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = indexLabel,
              fontFamily = NunitoSansFontFamily,
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = if (selected) Color.White else TextSlateSubdued
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
          text = text,
          fontFamily = NunitoSansFontFamily,
          fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
          fontSize = 17.sp,
          color = textColor
        )
      }

      if (isAudio && onAudioClick != null) {
        IconButton(
          onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onAudioClick()
          },
          modifier = Modifier.size(36.dp)
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Pronounce",
            tint = if (selected) EmeraldPrimary else CyanAccent
          )
        }
      }
    }
  }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

/**
 * Tactile Word Token Chip for word bank translation exercises.
 * When placed into answer, slot placeholder is shown.
 */
@Composable
fun TactileWordChip(
  word: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  isPlaced: Boolean = false,
  enabled: Boolean = true
) {
  val haptic = LocalHapticFeedback.current
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val shape = RoundedCornerShape(14.dp)

  if (isPlaced) {
    // Empty dashed slot placeholder
    Box(
      modifier = modifier
        .clip(shape)
        .background(SlotBackground, shape)
        .drawBehind {
          drawRoundRect(
            color = BorderSlateBase,
            cornerRadius = CornerRadius(14.dp.toPx()),
            style = Stroke(
              width = 2.dp.toPx(),
              pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
            )
          )
        }
        .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
      Text(
        text = word,
        color = Color.Transparent,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
      )
    }
  } else {
    val currentOffset by animateDpAsState(
      targetValue = if (isPressed) 2.dp else 0.dp,
      label = "chip_press"
    )

    Box(
      modifier = modifier
        .clip(shape)
        .background(BorderSlateBase, shape)
        .clickable(
          interactionSource = interactionSource,
          indication = null,
          enabled = enabled,
          onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
          }
        )
    ) {
      Box(
        modifier = Modifier
          .offset(y = currentOffset)
          .clip(shape)
          .background(SurfaceCard, shape)
          .border(2.dp, BorderSlate, shape)
          .padding(horizontal = 14.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = word,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = TextSlateHeadline
        )
      }
    }
  }
}

/**
 * Top HUD Bar with Streak 🔥, Gems 💎, Hearts ❤️ and Language Picker
 */
@Composable
fun TactileTopHudBar(
  currentLanguage: String,
  flagEmoji: String,
  streakDays: Int,
  gems: Int,
  hearts: Int,
  maxHearts: Int = 5,
  onLanguageClick: () -> Unit,
  onStreakClick: () -> Unit,
  onGemsClick: () -> Unit,
  onHeartsClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Language pill
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(SurfaceCard)
        .border(1.5.dp, BorderSlate, RoundedCornerShape(12.dp))
        .clickable { onLanguageClick() }
        .padding(horizontal = 10.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = flagEmoji, fontSize = 18.sp)
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = currentLanguage,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = TextSlateHeadline
      )
    }

    // Streak pill
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .clickable { onStreakClick() }
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.LocalFireDepartment,
        contentDescription = "Streak",
        tint = AmberStreak,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "$streakDays",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        color = AmberStreakBase
      )
    }

    // Gems pill
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .clickable { onGemsClick() }
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "💎", fontSize = 15.sp)
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "$gems",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        color = GemBlue
      )
    }

    // Hearts pill
    Row(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .clickable { onHeartsClick() }
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Hearts",
        tint = HeartRed,
        modifier = Modifier.size(19.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "$hearts",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 14.sp,
        color = HeartRed
      )
    }
  }
}

/**
 * Animated bottom feedback drawer for drill verification
 */
@Composable
fun DrillFeedbackSheet(
  isCorrect: Boolean,
  solutionExplanation: String? = null,
  onContinue: () -> Unit,
  modifier: Modifier = Modifier
) {
  val bgColor = if (isCorrect) EmeraldContainer else RubyContainer
  val textColor = if (isCorrect) EmeraldContainerText else RubyContainerText
  val iconColor = if (isCorrect) EmeraldPrimary else RubyError

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(bgColor)
      .padding(horizontal = 20.dp, vertical = 18.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Icon(
          imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
          contentDescription = if (isCorrect) "Correct" else "Incorrect",
          tint = iconColor,
          modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = if (isCorrect) "Nicely done!" else "Correct solution:",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 19.sp,
          color = textColor
        )
      }

      if (!isCorrect && !solutionExplanation.isNullOrBlank()) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = solutionExplanation,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          color = textColor,
          modifier = Modifier.padding(start = 38.dp)
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      TactileButton(
        onClick = onContinue,
        text = "CONTINUE",
        variant = if (isCorrect) TactileButtonVariant.PRIMARY else TactileButtonVariant.DANGER,
        modifier = Modifier.fillMaxWidth(),
        testTag = "drill_continue_button"
      )
    }
  }
}
