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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import com.example.model.LeaderboardUser
import com.example.ui.components.TactileCard
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldContainerText
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.EmeraldTinted
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued

@Composable
fun LeaderboardScreen(
  leagueName: String,
  daysLeft: Int,
  users: List<LeaderboardUser>,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .padding(horizontal = 20.dp),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    item {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(EmeraldContainer)
            .border(2.dp, EmeraldPrimary, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "League Trophy",
            tint = EmeraldPrimary,
            modifier = Modifier.size(42.dp)
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = leagueName,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 24.sp,
          color = TextSlateHeadline
        )

        Text(
          text = "Top 5 advance to the Diamond League • $daysLeft days left",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.SemiBold,
          fontSize = 13.sp,
          color = TextSlateSubdued
        )

        Spacer(modifier = Modifier.height(20.dp))
      }
    }

    itemsIndexed(users) { index, user ->
      val isPromotionCutoff = index == 4

      LeaderboardItemCard(
        user = user,
        rank = index + 1
      )

      if (isPromotionCutoff) {
        PromotionZoneDivider()
      } else {
        Spacer(modifier = Modifier.height(10.dp))
      }
    }
  }
}

@Composable
fun LeaderboardItemCard(
  user: LeaderboardUser,
  rank: Int,
  modifier: Modifier = Modifier
) {
  val isMe = user.isCurrentUser

  TactileCard(
    modifier = modifier.fillMaxWidth(),
    shapeRadius = 18.dp,
    backgroundColor = if (isMe) EmeraldTinted else SurfaceCard,
    borderColor = if (isMe) EmeraldPrimary else BorderSlate
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Rank Badge
      Box(
        modifier = Modifier.size(32.dp),
        contentAlignment = Alignment.Center
      ) {
        when (rank) {
          1 -> Text(text = "🥇", fontSize = 22.sp)
          2 -> Text(text = "🥈", fontSize = 22.sp)
          3 -> Text(text = "🥉", fontSize = 22.sp)
          else -> Text(
            text = "$rank",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            color = if (isMe) EmeraldPrimaryBase else TextSlateSubdued
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Avatar circle
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(
            if (isMe) EmeraldPrimary else Color(0xFFEFF4FF)
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = if (isMe) "🦉" else user.name.take(1),
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = if (isMe) 20.sp else 16.sp,
          color = if (isMe) Color.White else EmeraldPrimaryBase
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      // User name and promotion indicator
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = if (isMe) "${user.name} (You)" else user.name,
            fontFamily = NunitoSansFontFamily,
            fontWeight = if (isMe) FontWeight.ExtraBold else FontWeight.Bold,
            fontSize = 16.sp,
            color = if (isMe) EmeraldContainerText else TextSlateHeadline
          )
          if (user.isPromoted) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
              imageVector = Icons.Default.KeyboardArrowUp,
              contentDescription = "Promoting",
              tint = EmeraldPrimary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      // XP Count
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "${user.xp}",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 15.sp,
          color = TextSlateHeadline
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "XP",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          color = TextSlateSubdued
        )
      }
    }
  }
}

@Composable
fun PromotionZoneDivider() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(8.dp))
        .background(EmeraldContainer)
        .padding(horizontal = 14.dp, vertical = 4.dp)
    ) {
      Text(
        text = "PROMOTION ZONE",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 11.sp,
        letterSpacing = 0.08.sp,
        color = EmeraldContainerText
      )
    }
  }
}
