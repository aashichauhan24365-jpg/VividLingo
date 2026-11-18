package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.DailyQuest
import com.example.ui.components.TactileButton
import com.example.ui.components.TactileButtonVariant
import com.example.ui.components.TactileCard
import com.example.ui.theme.AmberContainer
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.BorderSlateBase
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldContainerText
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.GemBlue
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SlotBackground
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued

@Composable
fun QuestsStreakScreen(
  streakDays: Int,
  quests: List<DailyQuest>,
  onClaimQuest: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  val daysOfWeek = listOf("M", "T", "W", "T", "F", "S", "S")

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .padding(horizontal = 20.dp),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // Streak Hero Banner
    item {
      TactileCard(
        modifier = Modifier.fillMaxWidth(),
        shapeRadius = 24.dp,
        backgroundColor = AmberContainer,
        borderColor = AmberStreak,
        lipColor = AmberStreakBase
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(68.dp)
              .clip(CircleShape)
              .background(AmberStreak),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.LocalFireDepartment,
              contentDescription = "Flame",
              tint = Color.White,
              modifier = Modifier.size(44.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "$streakDays DAY STREAK!",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            color = AmberStreakBase
          )

          Text(
            text = "Practice every day to keep the flame burning",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF92400E)
          )

          Spacer(modifier = Modifier.height(18.dp))

          // 7-day calendar row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            daysOfWeek.forEachIndexed { index, day ->
              val isDone = index < streakDays.coerceAtMost(7)

              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                  modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isDone) AmberStreak else SurfaceCard)
                    .border(
                      1.5.dp,
                      if (isDone) AmberStreakBase else BorderSlate,
                      CircleShape
                    ),
                  contentAlignment = Alignment.Center
                ) {
                  if (isDone) {
                    Icon(
                      imageVector = Icons.Default.Check,
                      contentDescription = null,
                      tint = Color.White,
                      modifier = Modifier.size(20.dp)
                    )
                  }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = day,
                  fontFamily = NunitoSansFontFamily,
                  fontWeight = FontWeight.Bold,
                  fontSize = 12.sp,
                  color = Color(0xFF92400E)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(28.dp))
    }

    // Daily Quests Section
    item {
      Text(
        text = "DAILY QUESTS",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        letterSpacing = 0.08.sp,
        color = TextSlateSubdued
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    items(quests) { quest ->
      QuestItemCard(
        quest = quest,
        onClaim = { onClaimQuest(quest.id) }
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    // Achievements Section
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "ACHIEVEMENTS",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 13.sp,
        letterSpacing = 0.08.sp,
        color = TextSlateSubdued
      )
      Spacer(modifier = Modifier.height(12.dp))

      AchievementBadgeCard(
        title = "Vocabulary Virtuoso",
        desc = "Master 50 distinct words in Spanish",
        icon = Icons.Default.WorkspacePremium,
        isUnlocked = true
      )
      Spacer(modifier = Modifier.height(10.dp))
      AchievementBadgeCard(
        title = "Sharp Ears",
        desc = "Score 100% on 5 listening gym sessions",
        icon = Icons.Default.WorkspacePremium,
        isUnlocked = false
      )
    }
  }
}

@Composable
fun QuestItemCard(
  quest: DailyQuest,
  onClaim: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isCompleted = quest.currentProgress >= quest.maxProgress
  val progressFraction = (quest.currentProgress.toFloat() / quest.maxProgress.toFloat()).coerceIn(0f, 1f)

  TactileCard(
    modifier = modifier.fillMaxWidth(),
    shapeRadius = 20.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .background(EmeraldContainer),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "💎", fontSize = 22.sp)
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = quest.title,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = TextSlateHeadline
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Progress bar
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(9999.dp))
            .background(Color(0xFFE2E8F0))
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(progressFraction)
              .height(10.dp)
              .clip(RoundedCornerShape(9999.dp))
              .background(EmeraldPrimary)
          )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "${quest.currentProgress} / ${quest.maxProgress}",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.SemiBold,
          fontSize = 12.sp,
          color = TextSlateSubdued
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      if (quest.isClaimed) {
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(EmeraldContainer)
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
          Text(
            text = "CLAIMED",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = EmeraldContainerText
          )
        }
      } else if (isCompleted) {
        TactileButton(
          onClick = onClaim,
          text = "CLAIM",
          variant = TactileButtonVariant.SECONDARY,
          lipHeight = 2.dp,
          contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp),
          testTag = "claim_quest_${quest.id}"
        )
      } else {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SlotBackground)
            .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Text(text = "+${quest.gemReward}", fontFamily = NunitoSansFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = GemBlue)
          Spacer(modifier = Modifier.width(2.dp))
          Text(text = "💎", fontSize = 12.sp)
        }
      }
    }
  }
}

@Composable
fun AchievementBadgeCard(
  title: String,
  desc: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  isUnlocked: Boolean,
  modifier: Modifier = Modifier
) {
  TactileCard(
    modifier = modifier.fillMaxWidth(),
    shapeRadius = 18.dp,
    backgroundColor = if (isUnlocked) SurfaceCard else Color(0xFFF1F5F9)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .background(if (isUnlocked) AmberStreak else Color(0xFFCBD5E1)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = Color.White,
          modifier = Modifier.size(26.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = if (isUnlocked) TextSlateHeadline else TextSlateMuted
        )
        Text(
          text = desc,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 13.sp,
          color = TextSlateSubdued
        )
      }

      if (isUnlocked) {
        Box(
          modifier = Modifier
            .clip(CircleShape)
            .background(EmeraldContainer)
            .padding(6.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Unlocked",
            tint = EmeraldPrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }
    }
  }
}
