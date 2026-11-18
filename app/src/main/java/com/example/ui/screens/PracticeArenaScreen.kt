package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurriculumData
import com.example.model.LessonNode
import com.example.ui.components.TactileButton
import com.example.ui.components.TactileButtonVariant
import com.example.ui.components.TactileCard
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.CyanAccentBase
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.RubyError
import com.example.ui.theme.RubyErrorBase
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateSubdued

@Composable
fun PracticeArenaScreen(
  onStartDrills: (LessonNode) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedGrammarTip by remember { mutableStateOf<String?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .padding(horizontal = 20.dp),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    item {
      Text(
        text = "Practice Arena",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        color = TextSlateHeadline
      )
      Text(
        text = "Sharpen your memory with targeted drills",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        color = TextSlateSubdued
      )
      Spacer(modifier = Modifier.height(20.dp))
    }

    item {
      PracticeCard(
        title = "Speed Matching",
        subtitle = "Match 8 vocabulary pairs against the clock",
        badge = "+10 XP",
        icon = Icons.Default.ElectricBolt,
        themeColor = AmberStreak,
        baseColor = AmberStreakBase,
        onClick = {
          val dummyNode = LessonNode(
            id = "practice_speed",
            unitId = 1,
            title = "Speed Matching",
            subtitle = "Vocab pairing sprint",
            drills = CurriculumData.createSpanishCafeDrills()
          )
          onStartDrills(dummyNode)
        }
      )
      Spacer(modifier = Modifier.height(14.dp))
    }

    item {
      PracticeCard(
        title = "Listening Gym",
        subtitle = "Train your ears with authentic speaker soundbites",
        badge = "+15 XP",
        icon = Icons.Default.Headphones,
        themeColor = CyanAccent,
        baseColor = CyanAccentBase,
        onClick = {
          val dummyNode = LessonNode(
            id = "practice_listening",
            unitId = 1,
            title = "Listening Gym",
            subtitle = "Audio comprehension",
            drills = CurriculumData.createSpanishPhrasesDrills()
          )
          onStartDrills(dummyNode)
        }
      )
      Spacer(modifier = Modifier.height(14.dp))
    }

    item {
      PracticeCard(
        title = "Mistakes Review",
        subtitle = "Review sentences you recently missed",
        badge = "Smart Review",
        icon = Icons.Default.Refresh,
        themeColor = RubyError,
        baseColor = RubyErrorBase,
        onClick = {
          val dummyNode = LessonNode(
            id = "practice_mistakes",
            unitId = 1,
            title = "Mistakes Review",
            subtitle = "Fix weak spots",
            drills = CurriculumData.createSpanishGreetingsDrills()
          )
          onStartDrills(dummyNode)
        }
      )
      Spacer(modifier = Modifier.height(24.dp))
    }

    item {
      Text(
        text = "GRAMMAR CHEAT SHEET",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        letterSpacing = 0.08.sp,
        color = TextSlateSubdued
      )
      Spacer(modifier = Modifier.height(12.dp))

      TactileCard(
        modifier = Modifier.fillMaxWidth(),
        shapeRadius = 20.dp
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(EmeraldContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = EmeraldPrimary,
                modifier = Modifier.size(22.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Ser vs. Estar (To Be)",
                fontFamily = NunitoSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextSlateHeadline
              )
              Text(
                text = "Permanent traits vs. temporary states",
                fontFamily = NunitoSansFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = TextSlateSubdued
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "• Use 'SER' for identity, origin, and characteristics (Soy estudiante).\n• Use 'ESTAR' for location, emotions, and conditions (Estoy en casa).",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = TextSlateBody
          )
        }
      }
    }
  }
}

@Composable
fun PracticeCard(
  title: String,
  subtitle: String,
  badge: String,
  icon: ImageVector,
  themeColor: Color,
  baseColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  TactileCard(
    modifier = modifier.fillMaxWidth(),
    shapeRadius = 20.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(18.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(54.dp)
          .clip(CircleShape)
          .background(themeColor),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = Color.White,
          modifier = Modifier.size(28.dp)
        )
      }

      Spacer(modifier = Modifier.width(16.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = title,
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 17.sp,
            color = TextSlateHeadline
          )

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(themeColor.copy(alpha = 0.15f))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = badge,
              fontFamily = NunitoSansFontFamily,
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp,
              color = baseColor
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = subtitle,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 13.sp,
          color = TextSlateSubdued
        )
      }
    }
  }
}
