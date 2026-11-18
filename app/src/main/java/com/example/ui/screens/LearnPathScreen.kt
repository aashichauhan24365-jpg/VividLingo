package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LearningUnit
import com.example.model.LessonIconType
import com.example.model.LessonNode
import com.example.ui.components.TactileButton
import com.example.ui.components.TactileButtonVariant
import com.example.ui.components.TactileCard
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.BorderSlateBase
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldContainerText
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued

@Composable
fun LearnPathScreen(
  units: List<LearningUnit>,
  onNodeClick: (LessonNode) -> Unit,
  selectedNodeModal: LessonNode?,
  onDismissModal: () -> Unit,
  onStartLesson: (LessonNode) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize().background(CanvasBackground)) {
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 96.dp)
    ) {
      units.forEach { unit ->
        item(key = "unit_header_${unit.id}") {
          UnitHeaderCard(unit = unit)
        }

        item(key = "unit_path_${unit.id}") {
          SerpentinePathBlock(
            nodes = unit.nodes,
            onNodeClick = onNodeClick
          )
        }
      }
    }

    // Modal when a node is tapped
    if (selectedNodeModal != null) {
      LessonStartDialog(
        node = selectedNodeModal,
        onDismiss = onDismissModal,
        onStart = { onStartLesson(selectedNodeModal) }
      )
    }
  }
}

@Composable
fun UnitHeaderCard(
  unit: LearningUnit,
  modifier: Modifier = Modifier
) {
  val bannerColor = Color(unit.bannerColorHex)
  val darkBase = when (unit.bannerColorHex) {
    0xFF10B981 -> EmeraldPrimaryBase
    0xFF06B6D4 -> Color(0xFF0891B2)
    else -> AmberStreakBase
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(darkBase)
      .padding(bottom = 4.dp)
      .clip(RoundedCornerShape(20.dp))
      .background(bannerColor)
      .padding(20.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = unit.title.uppercase(),
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 13.sp,
          letterSpacing = 0.08.sp,
          color = Color.White.copy(alpha = 0.9f)
        )

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White.copy(alpha = 0.25f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(
            text = "SECTION ${unit.number}",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = unit.description,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        color = Color.White
      )
    }
  }
}

/**
 * Serpentine Path: Circular 68dp lesson nodes positioned in an organic snake curve
 */
@Composable
fun SerpentinePathBlock(
  nodes: List<LessonNode>,
  onNodeClick: (LessonNode) -> Unit,
  modifier: Modifier = Modifier
) {
  // Horizontal offsets for nodes to produce the serpentine wave:
  // e.g. center (0), right (+55), right (+40), center (0), left (-45), left (-55)
  val offsets = listOf(0, 50, 60, 10, -50, -60, -10, 45, 55)

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    nodes.forEachIndexed { index, node ->
      val xOffset = offsets.getOrElse(index) { 0 }

      // Node item with pointer for active lesson
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
      ) {
        Box(modifier = Modifier.offset(x = xOffset.dp)) {
          LessonPathNodeButton(
            node = node,
            onClick = { onNodeClick(node) }
          )

          // If node is current/active, show a friendly floating speech bubble
          if (node.isCurrent) {
            FloatingStartBubble(
              modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-44).dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun LessonPathNodeButton(
  node: LessonNode,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val haptic = LocalHapticFeedback.current
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  val isChest = node.iconType == LessonIconType.CHEST
  val isTrophy = node.iconType == LessonIconType.TROPHY

  val (faceColor, baseColor, iconColor) = when {
    node.isCompleted -> Triple(EmeraldPrimary, EmeraldPrimaryBase, Color.White)
    node.isCurrent -> Triple(AmberStreak, AmberStreakBase, Color.White)
    node.isLocked -> Triple(Color(0xFFE2E8F0), Color(0xFFCBD5E1), Color(0xFF94A3B8))
    else -> Triple(EmeraldPrimary, EmeraldPrimaryBase, Color.White)
  }

  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseScale by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 1.08f,
    animationSpec = infiniteRepeatable(
      animation = tween(900, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulse_scale"
  )

  val nodeSize = if (isChest || isTrophy) 72.dp else 66.dp
  val currentPressOffset = if (isPressed && !node.isLocked) 4.dp else 0.dp

  Box(
    modifier = modifier
      .size(nodeSize + 12.dp)
      .testTag("lesson_node_${node.id}"),
    contentAlignment = Alignment.Center
  ) {
    // Pulsing halo ring for active node
    if (node.isCurrent) {
      Box(
        modifier = Modifier
          .size(nodeSize + 14.dp)
          .scale(pulseScale)
          .clip(CircleShape)
          .background(AmberStreak.copy(alpha = 0.22f))
      )
    }

    // 3D tactile node
    Box(
      modifier = Modifier
        .size(nodeSize)
        .clip(CircleShape)
        .background(baseColor)
        .clickable(
          interactionSource = interactionSource,
          indication = null,
          enabled = !node.isLocked,
          onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            onClick()
          }
        )
    ) {
      // Face
      Box(
        modifier = Modifier
          .size(nodeSize)
          .offset(y = currentPressOffset)
          .clip(CircleShape)
          .background(faceColor)
          .then(
            if (node.isCurrent) Modifier.border(3.dp, Color.White, CircleShape)
            else Modifier
          ),
        contentAlignment = Alignment.Center
      ) {
        val icon = getIconForNode(node.iconType, node.isLocked, node.isCompleted)
        Icon(
          imageVector = icon,
          contentDescription = node.title,
          tint = iconColor,
          modifier = Modifier.size(if (isChest || isTrophy) 34.dp else 30.dp)
        )

        // Star badge for completed nodes
        if (node.isCompleted) {
          Box(
            modifier = Modifier
              .align(Alignment.BottomEnd)
              .offset(x = 2.dp, y = 2.dp)
              .size(24.dp)
              .clip(CircleShape)
              .background(AmberStreak)
              .border(1.5.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = "Star",
              tint = Color.White,
              modifier = Modifier.size(14.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
fun FloatingStartBubble(modifier: Modifier = Modifier) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(AmberStreak)
      .border(2.dp, AmberStreakBase, RoundedCornerShape(12.dp))
      .padding(horizontal = 14.dp, vertical = 6.dp)
  ) {
    Text(
      text = "START",
      fontFamily = NunitoSansFontFamily,
      fontWeight = FontWeight.ExtraBold,
      fontSize = 13.sp,
      letterSpacing = 0.06.sp,
      color = Color.White
    )
  }
}

private fun getIconForNode(
  type: LessonIconType,
  isLocked: Boolean,
  isCompleted: Boolean
): ImageVector {
  if (isLocked) return Icons.Default.Lock
  if (isCompleted) return Icons.Default.Check
  return when (type) {
    LessonIconType.GREETING -> Icons.Default.WavingHand
    LessonIconType.FOOD -> Icons.Default.Fastfood
    LessonIconType.TRAVEL -> Icons.Default.Flight
    LessonIconType.BOOK -> Icons.Default.Book
    LessonIconType.CHEST -> Icons.Default.CardGiftcard
    LessonIconType.TROPHY -> Icons.Default.EmojiEvents
    else -> Icons.Default.PlayArrow
  }
}

@Composable
fun LessonStartDialog(
  node: LessonNode,
  onDismiss: () -> Unit,
  onStart: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(24.dp),
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(EmeraldContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = getIconForNode(node.iconType, false, node.isCompleted),
            contentDescription = null,
            tint = EmeraldPrimary,
            modifier = Modifier.size(36.dp)
          )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = node.title,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 22.sp,
          color = TextSlateHeadline,
          textAlign = TextAlign.Center
        )
      }
    },
    text = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
          text = node.subtitle,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 15.sp,
          color = TextSlateSubdued,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFFEF3C7))
            .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
          Text(text = "⚡", fontSize = 16.sp)
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "+${node.xpReward} XP Reward",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = Color(0xFF92400E)
          )
        }
      }
    },
    confirmButton = {
      TactileButton(
        onClick = onStart,
        text = if (node.isCompleted) "PRACTICE AGAIN (+5 XP)" else "START LESSON",
        variant = TactileButtonVariant.PRIMARY,
        modifier = Modifier.fillMaxWidth(),
        testTag = "start_lesson_confirm_btn"
      )
    },
    dismissButton = {
      TactileButton(
        onClick = onDismiss,
        text = "LATER",
        variant = TactileButtonVariant.OUTLINE,
        modifier = Modifier.fillMaxWidth(),
        testTag = "dismiss_lesson_dialog_btn"
      )
    }
  )
}
