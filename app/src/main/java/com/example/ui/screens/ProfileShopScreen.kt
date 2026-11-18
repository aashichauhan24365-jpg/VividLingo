package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.ShopItem
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
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.GemBlue
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued

@Composable
fun ProfileShopScreen(
  streakDays: Int,
  totalXp: Int,
  gems: Int,
  hearts: Int,
  league: String,
  shopItems: List<ShopItem>,
  onBuyItem: (ShopItem) -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .padding(horizontal = 20.dp),
    contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // User Profile Header
    item {
      TactileCard(
        modifier = Modifier.fillMaxWidth(),
        shapeRadius = 24.dp
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          // Owl mascot avatar
          Image(
            painter = painterResource(id = R.drawable.ic_app_mascot),
            contentDescription = "User Avatar",
            modifier = Modifier
              .size(90.dp)
              .clip(CircleShape)
              .border(3.dp, EmeraldPrimary, CircleShape),
            contentScale = ContentScale.Crop
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "Polyglot Pioneer",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            color = TextSlateHeadline
          )

          Text(
            text = "Member since October 2026",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = TextSlateSubdued
          )
        }
      }

      Spacer(modifier = Modifier.height(18.dp))
    }

    // 2x2 Stats Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        StatPill(
          icon = "⚡",
          value = "$totalXp",
          label = "Total XP",
          modifier = Modifier.weight(1f)
        )
        StatPill(
          icon = "🔥",
          value = "$streakDays",
          label = "Day Streak",
          modifier = Modifier.weight(1f)
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        StatPill(
          icon = "🏆",
          value = league.split(" ").firstOrNull() ?: "Emerald",
          label = "Current League",
          modifier = Modifier.weight(1f)
        )
        StatPill(
          icon = "📚",
          value = "142",
          label = "Words Learned",
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(28.dp))
    }

    // Shop Section Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "LINGO SHOP",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 13.sp,
          letterSpacing = 0.08.sp,
          color = TextSlateSubdued
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = "💎", fontSize = 16.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "$gems Gems",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 14.sp,
            color = GemBlue
          )
        }
      }
      Spacer(modifier = Modifier.height(12.dp))
    }

    items(shopItems) { item ->
      ShopItemCard(
        item = item,
        canAfford = gems >= item.costGems,
        onBuy = { onBuyItem(item) }
      )
      Spacer(modifier = Modifier.height(12.dp))
    }
  }
}

@Composable
fun StatPill(
  icon: String,
  value: String,
  label: String,
  modifier: Modifier = Modifier
) {
  TactileCard(
    modifier = modifier,
    shapeRadius = 18.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = icon, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = value,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 18.sp,
          color = TextSlateHeadline
        )
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = label,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = TextSlateSubdued
      )
    }
  }
}

@Composable
fun ShopItemCard(
  item: ShopItem,
  canAfford: Boolean,
  onBuy: () -> Unit,
  modifier: Modifier = Modifier
) {
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
          .size(52.dp)
          .clip(CircleShape)
          .background(Color(0xFFEFF4FF)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = item.iconEmoji, fontSize = 26.sp)
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = item.title,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 16.sp,
          color = TextSlateHeadline
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = item.description,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 13.sp,
          lineHeight = 18.sp,
          color = TextSlateSubdued
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      TactileButton(
        onClick = onBuy,
        enabled = canAfford,
        variant = if (canAfford) TactileButtonVariant.SECONDARY else TactileButtonVariant.OUTLINE,
        lipHeight = 2.dp,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        testTag = "buy_${item.id}"
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "${item.costGems}",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp,
            color = if (canAfford) Color.White else TextSlateMuted
          )
          Spacer(modifier = Modifier.width(3.dp))
          Text(text = "💎", fontSize = 12.sp)
        }
      }
    }
  }
}
