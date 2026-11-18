package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Language
import com.example.ui.components.TactileButton
import com.example.ui.components.TactileButtonVariant
import com.example.ui.components.TactileTopHudBar
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.LearnPathScreen
import com.example.ui.screens.LessonDrillScreen
import com.example.ui.screens.PracticeArenaScreen
import com.example.ui.screens.ProfileShopScreen
import com.example.ui.screens.QuestsStreakScreen
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.BorderSlateBase
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldContainerText
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.EmeraldTinted
import com.example.ui.theme.HeartRed
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateMuted
import com.example.ui.theme.TextSlateSubdued
import com.example.ui.theme.VividLingoTheme
import com.example.viewmodel.AppTab
import com.example.viewmodel.VividLingoViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: VividLingoViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContent {
      VividLingoTheme {
        val state by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current

        LaunchedEffect(state.feedbackToast) {
          state.feedbackToast?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearFeedbackToast()
          }
        }

        // Active Lesson Screen takes over entire screen
        if (state.activeSession != null) {
          BackHandler {
            viewModel.exitLesson()
          }

          LessonDrillScreen(
            session = state.activeSession!!,
            hearts = state.hearts,
            onExit = { viewModel.exitLesson() },
            onSelectOption = { viewModel.selectMultipleChoiceOption(it) },
            onAddToken = { viewModel.addWordToken(it) },
            onRemoveToken = { viewModel.removeWordToken(it) },
            onSelectPairPrompt = { viewModel.selectPairPrompt(it) },
            onSelectPairTranslation = { viewModel.selectPairTranslation(it) },
            onCheckAnswer = { viewModel.checkCurrentAnswer() },
            onContinue = { viewModel.continueSession() },
            onSpeak = { viewModel.speak(it) },
            modifier = Modifier
              .fillMaxSize()
              .statusBarsPadding()
              .navigationBarsPadding()
          )
        } else {
          // Main Dashboard with Top HUD and Bottom Navigation
          Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = CanvasBackground,
            topBar = {
              TactileTopHudBar(
                currentLanguage = state.currentLanguage.name,
                flagEmoji = state.currentLanguage.flagEmoji,
                streakDays = state.streakDays,
                gems = state.gems,
                hearts = state.hearts,
                maxHearts = state.maxHearts,
                onLanguageClick = { viewModel.toggleLanguageDialog(true) },
                onStreakClick = { viewModel.toggleStreakDialog(true) },
                onGemsClick = { viewModel.selectTab(AppTab.PROFILE) },
                onHeartsClick = { viewModel.toggleHeartsDialog(true) },
                modifier = Modifier.statusBarsPadding()
              )
            },
            bottomBar = {
              VividBottomNav(
                currentTab = state.activeTab,
                onTabSelect = { viewModel.selectTab(it) },
                modifier = Modifier.navigationBarsPadding()
              )
            }
          ) { innerPadding ->
            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
              when (state.activeTab) {
                AppTab.LEARN -> {
                  LearnPathScreen(
                    units = state.units,
                    onNodeClick = { viewModel.openNodeModal(it) },
                    selectedNodeModal = state.selectedNodeModal,
                    onDismissModal = { viewModel.dismissNodeModal() },
                    onStartLesson = { viewModel.startLesson(it) }
                  )
                }
                AppTab.PRACTICE -> {
                  PracticeArenaScreen(
                    onStartDrills = { viewModel.startLesson(it) }
                  )
                }
                AppTab.LEADERBOARD -> {
                  LeaderboardScreen(
                    leagueName = state.currentLeague,
                    daysLeft = state.daysLeftInLeague,
                    users = state.leaderboard
                  )
                }
                AppTab.QUESTS -> {
                  QuestsStreakScreen(
                    streakDays = state.streakDays,
                    quests = state.quests,
                    onClaimQuest = { viewModel.claimQuest(it) }
                  )
                }
                AppTab.PROFILE -> {
                  ProfileShopScreen(
                    streakDays = state.streakDays,
                    totalXp = state.totalXp,
                    gems = state.gems,
                    hearts = state.hearts,
                    league = state.currentLeague,
                    shopItems = state.shopItems,
                    onBuyItem = { viewModel.buyShopItem(it) }
                  )
                }
              }
            }
          }

          // Dialogs
          if (state.showLanguageDialog) {
            LanguageSelectDialog(
              languages = state.languages,
              currentLang = state.currentLanguage,
              onSelect = { viewModel.switchLanguage(it) },
              onDismiss = { viewModel.toggleLanguageDialog(false) }
            )
          }

          if (state.showHeartsDialog) {
            HeartsRefillDialog(
              currentHearts = state.hearts,
              maxHearts = state.maxHearts,
              gems = state.gems,
              onRefill = {
                val refillItem = state.shopItems.find { it.id == "shop_heart_refill" }
                if (refillItem != null) {
                  viewModel.buyShopItem(refillItem)
                }
                viewModel.toggleHeartsDialog(false)
              },
              onDismiss = { viewModel.toggleHeartsDialog(false) }
            )
          }

          if (state.showStreakDialog) {
            StreakInfoDialog(
              streakDays = state.streakDays,
              onDismiss = { viewModel.toggleStreakDialog(false) }
            )
          }

          if (state.showChestCelebration) {
            ChestRewardDialog(
              onDismiss = { viewModel.dismissChestCelebration() }
            )
          }
        }
      }
    }
  }
}

/**
 * Tactile Bottom Navigation Bar
 */
@Composable
fun VividBottomNav(
  currentTab: AppTab,
  onTabSelect: (AppTab) -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(SurfaceCard)
      .border(1.dp, BorderSlate)
      .padding(horizontal = 8.dp, vertical = 6.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavTabButton(
        icon = Icons.Default.School,
        label = "Learn",
        isSelected = currentTab == AppTab.LEARN,
        onClick = { onTabSelect(AppTab.LEARN) },
        testTag = "nav_learn"
      )

      NavTabButton(
        icon = Icons.Default.ElectricBolt,
        label = "Practice",
        isSelected = currentTab == AppTab.PRACTICE,
        onClick = { onTabSelect(AppTab.PRACTICE) },
        testTag = "nav_practice"
      )

      NavTabButton(
        icon = Icons.Default.EmojiEvents,
        label = "Leagues",
        isSelected = currentTab == AppTab.LEADERBOARD,
        onClick = { onTabSelect(AppTab.LEADERBOARD) },
        testTag = "nav_leagues"
      )

      NavTabButton(
        icon = Icons.Default.LocalFireDepartment,
        label = "Quests",
        isSelected = currentTab == AppTab.QUESTS,
        onClick = { onTabSelect(AppTab.QUESTS) },
        testTag = "nav_quests"
      )

      NavTabButton(
        icon = Icons.Default.Person,
        label = "Profile",
        isSelected = currentTab == AppTab.PROFILE,
        onClick = { onTabSelect(AppTab.PROFILE) },
        testTag = "nav_profile"
      )
    }
  }
}

@Composable
fun NavTabButton(
  icon: ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  Box(
    modifier = Modifier
      .testTag(testTag)
      .clip(RoundedCornerShape(14.dp))
      .background(if (isSelected) EmeraldContainer else Color.Transparent)
      .clickable(onClick = onClick)
      .padding(horizontal = 14.dp, vertical = 8.dp),
    contentAlignment = Alignment.Center
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Icon(
        imageVector = icon,
        contentDescription = label,
        tint = if (isSelected) EmeraldPrimary else TextSlateSubdued,
        modifier = Modifier.size(24.dp)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = label,
        fontFamily = NunitoSansFontFamily,
        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
        fontSize = 11.sp,
        color = if (isSelected) EmeraldContainerText else TextSlateSubdued
      )
    }
  }
}

@Composable
fun LanguageSelectDialog(
  languages: List<Language>,
  currentLang: Language,
  onSelect: (Language) -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(24.dp),
    title = {
      Text(
        text = "Select Course",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        color = TextSlateHeadline
      )
    },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        languages.forEach { lang ->
          val isSelected = lang.id == currentLang.id
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(if (isSelected) EmeraldTinted else SurfaceCard)
              .border(
                1.5.dp,
                if (isSelected) EmeraldPrimary else BorderSlate,
                RoundedCornerShape(14.dp)
              )
              .clickable { onSelect(lang) }
              .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = lang.flagEmoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "${lang.name} (${lang.nativeName})",
                fontFamily = NunitoSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextSlateHeadline
              )
              Text(
                text = lang.learnersCount,
                fontFamily = NunitoSansFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = TextSlateSubdued
              )
            }
          }
        }
      }
    },
    confirmButton = {
      TactileButton(
        onClick = onDismiss,
        text = "CLOSE",
        variant = TactileButtonVariant.OUTLINE,
        modifier = Modifier.fillMaxWidth()
      )
    }
  )
}

@Composable
fun HeartsRefillDialog(
  currentHearts: Int,
  maxHearts: Int,
  gems: Int,
  onRefill: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(24.dp),
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFFFFE4E6)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = HeartRed,
            modifier = Modifier.size(36.dp)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Hearts: $currentHearts / $maxHearts",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 20.sp,
          color = TextSlateHeadline
        )
      }
    },
    text = {
      Text(
        text = "Hearts allow you to make mistakes during lessons without failing. Refill all 5 hearts instantly for 150 Gems, or practice in the Arena to recover them!",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = TextSlateSubdued,
        textAlign = TextAlign.Center
      )
    },
    confirmButton = {
      TactileButton(
        onClick = onRefill,
        text = "REFILL HEARTS (150 💎)",
        enabled = gems >= 150 && currentHearts < maxHearts,
        variant = TactileButtonVariant.SECONDARY,
        modifier = Modifier.fillMaxWidth()
      )
    },
    dismissButton = {
      TactileButton(
        onClick = onDismiss,
        text = "OKAY",
        variant = TactileButtonVariant.OUTLINE,
        modifier = Modifier.fillMaxWidth()
      )
    }
  )
}

@Composable
fun StreakInfoDialog(
  streakDays: Int,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(24.dp),
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(AmberStreak),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.LocalFireDepartment,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(36.dp)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "$streakDays Day Streak!",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 22.sp,
          color = AmberStreakBase
        )
      }
    },
    text = {
      Text(
        text = "You're on a $streakDays-day streak! Complete a lesson every day to build lasting fluency habits and earn bonus XP.",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = TextSlateSubdued,
        textAlign = TextAlign.Center
      )
    },
    confirmButton = {
      TactileButton(
        onClick = onDismiss,
        text = "KEEP IT UP!",
        variant = TactileButtonVariant.PRIMARY,
        modifier = Modifier.fillMaxWidth()
      )
    }
  )
}

@Composable
fun ChestRewardDialog(
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = SurfaceCard,
    shape = RoundedCornerShape(24.dp),
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(text = "🎁", fontSize = 48.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Treasure Unlocked!",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 22.sp,
          color = EmeraldPrimaryBase
        )
      }
    },
    text = {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
          text = "You reached a milestone along your learning path!",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 14.sp,
          color = TextSlateSubdued,
          textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(EmeraldContainer)
            .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
          Text(
            text = "+50 Gems 💎  &  +25 XP ⚡",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            color = EmeraldContainerText
          )
        }
      }
    },
    confirmButton = {
      TactileButton(
        onClick = onDismiss,
        text = "AWESOME!",
        variant = TactileButtonVariant.PRIMARY,
        modifier = Modifier.fillMaxWidth()
      )
    }
  )
}
