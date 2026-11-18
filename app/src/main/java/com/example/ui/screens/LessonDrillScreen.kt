package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Drill
import com.example.model.DrillType
import com.example.ui.components.DrillFeedbackSheet
import com.example.ui.components.TactileButton
import com.example.ui.components.TactileButtonVariant
import com.example.ui.components.TactileCard
import com.example.ui.components.TactileSelectableTile
import com.example.ui.components.TactileWordChip
import com.example.ui.theme.AmberStreak
import com.example.ui.theme.AmberStreakBase
import com.example.ui.theme.BorderSlate
import com.example.ui.theme.CanvasBackground
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EmeraldContainer
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.theme.EmeraldPrimaryBase
import com.example.ui.theme.HeartRed
import com.example.ui.theme.NunitoSansFontFamily
import com.example.ui.theme.SlotBackground
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.TextSlateBody
import com.example.ui.theme.TextSlateHeadline
import com.example.ui.theme.TextSlateSubdued
import com.example.viewmodel.ActiveLessonSession

@Composable
fun LessonDrillScreen(
  session: ActiveLessonSession,
  hearts: Int,
  onExit: () -> Unit,
  onSelectOption: (Int) -> Unit,
  onAddToken: (String) -> Unit,
  onRemoveToken: (Int) -> Unit,
  onSelectPairPrompt: (String) -> Unit,
  onSelectPairTranslation: (String) -> Unit,
  onCheckAnswer: () -> Unit,
  onContinue: () -> Unit,
  onSpeak: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var showExitConfirmDialog by remember { mutableStateOf(false) }

  if (session.isCompleted) {
    LessonCompletedCelebration(
      xpGained = session.xpGained,
      mistakesCount = session.mistakesCount,
      onFinish = onExit
    )
    return
  }

  val drills = session.node.drills
  val currentDrill = drills.getOrNull(session.currentDrillIndex) ?: return

  val progress = if (drills.isNotEmpty()) {
    (session.currentDrillIndex.toFloat() / drills.size.toFloat())
  } else 0f

  val animatedProgress by animateFloatAsState(targetValue = progress, label = "lesson_progress")

  // Check if answer input is present to enable CHECK button
  val isCheckEnabled = when (currentDrill.type) {
    DrillType.MULTIPLE_CHOICE, DrillType.LISTENING_DRILL, DrillType.FILL_BLANK -> {
      session.selectedOptionIndex != null
    }
    DrillType.WORD_BANK_TRANSLATION -> {
      session.placedTokens.isNotEmpty()
    }
    DrillType.PAIR_MATCHING -> {
      currentDrill.pairs.all {
        session.matchedPairKeys.contains(it.prompt) && session.matchedPairKeys.contains(it.translation)
      }
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(CanvasBackground)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = if (session.isEvaluating) 140.dp else 90.dp)
    ) {
      // Top Lesson Navigation Bar
      LessonTopHeader(
        progress = animatedProgress,
        hearts = hearts,
        onCloseClick = { showExitConfirmDialog = true }
      )

      // Drill Content Area
      Column(
        modifier = Modifier
          .fillMaxSize()
          .verticalScroll(rememberScrollState())
          .padding(horizontal = 20.dp, vertical = 12.dp)
      ) {
        // Instruction Header
        Text(
          text = currentDrill.instruction,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = TextSlateSubdued,
          letterSpacing = 0.02.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Main Exercise Type
        when (currentDrill.type) {
          DrillType.WORD_BANK_TRANSLATION -> {
            WordBankDrillView(
              drill = currentDrill,
              session = session,
              onAddToken = onAddToken,
              onRemoveToken = onRemoveToken,
              onSpeak = onSpeak
            )
          }
          DrillType.MULTIPLE_CHOICE -> {
            MultipleChoiceDrillView(
              drill = currentDrill,
              session = session,
              onSelectOption = onSelectOption,
              onSpeak = onSpeak
            )
          }
          DrillType.LISTENING_DRILL -> {
            ListeningDrillView(
              drill = currentDrill,
              session = session,
              onSelectOption = onSelectOption,
              onSpeak = onSpeak
            )
          }
          DrillType.PAIR_MATCHING -> {
            PairMatchingDrillView(
              drill = currentDrill,
              session = session,
              onSelectPrompt = onSelectPairPrompt,
              onSelectTranslation = onSelectPairTranslation
            )
          }
          DrillType.FILL_BLANK -> {
            FillBlankDrillView(
              drill = currentDrill,
              session = session,
              onSelectOption = onSelectOption
            )
          }
        }
      }
    }

    // Bottom Sticky Evaluation Banner / Check Button
    Box(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
    ) {
      if (session.isEvaluating && session.isCorrect != null) {
        DrillFeedbackSheet(
          isCorrect = session.isCorrect,
          solutionExplanation = session.explanation,
          onContinue = onContinue,
          modifier = Modifier.fillMaxWidth()
        )
      } else {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceCard)
            .border(1.dp, BorderSlate)
            .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
          TactileButton(
            onClick = onCheckAnswer,
            text = "CHECK",
            enabled = isCheckEnabled,
            variant = TactileButtonVariant.PRIMARY,
            modifier = Modifier.fillMaxWidth(),
            testTag = "drill_check_button"
          )
        }
      }
    }

    // Exit confirmation dialog
    if (showExitConfirmDialog) {
      AlertDialog(
        onDismissRequest = { showExitConfirmDialog = false },
        containerColor = SurfaceCard,
        shape = RoundedCornerShape(20.dp),
        title = {
          Text(
            text = "Leave lesson?",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = TextSlateHeadline
          )
        },
        text = {
          Text(
            text = "All progress in this session will be lost. Are you sure you want to quit?",
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = TextSlateSubdued
          )
        },
        confirmButton = {
          TactileButton(
            onClick = {
              showExitConfirmDialog = false
              onExit()
            },
            text = "QUIT",
            variant = TactileButtonVariant.DANGER,
            modifier = Modifier.fillMaxWidth()
          )
        },
        dismissButton = {
          TactileButton(
            onClick = { showExitConfirmDialog = false },
            text = "KEEP LEARNING",
            variant = TactileButtonVariant.PRIMARY,
            modifier = Modifier.fillMaxWidth()
          )
        }
      )
    }
  }
}

@Composable
fun LessonTopHeader(
  progress: Float,
  hearts: Int,
  onCloseClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    IconButton(
      onClick = onCloseClick,
      modifier = Modifier.size(36.dp)
    ) {
      Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Exit lesson",
        tint = TextSlateSubdued
      )
    }

    Spacer(modifier = Modifier.width(8.dp))

    // Rounded Pill Progress Bar
    Box(
      modifier = Modifier
        .weight(1f)
        .height(14.dp)
        .clip(RoundedCornerShape(9999.dp))
        .background(Color(0xFFE2E8F0))
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth(progress.coerceIn(0.04f, 1f))
          .height(14.dp)
          .clip(RoundedCornerShape(9999.dp))
          .background(EmeraldPrimary)
      )
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Hearts indicator
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "Hearts remaining",
        tint = HeartRed,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "$hearts",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp,
        color = HeartRed
      )
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordBankDrillView(
  drill: Drill,
  session: ActiveLessonSession,
  onAddToken: (String) -> Unit,
  onRemoveToken: (Int) -> Unit,
  onSpeak: (String) -> Unit
) {
  Column {
    // Prompt with speaker
    PromptCardWithSpeaker(
      prompt = drill.prompt,
      audioText = drill.audioText,
      onSpeak = onSpeak
    )

    Spacer(modifier = Modifier.height(28.dp))

    // Sentence slot answer line (placed tokens)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(SurfaceCard)
        .border(2.dp, BorderSlate, RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      if (session.placedTokens.isEmpty()) {
        Text(
          text = "Tap word tokens below to construct your answer",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Medium,
          fontSize = 15.sp,
          color = TextSlateSubdued,
          modifier = Modifier.padding(vertical = 10.dp)
        )
      } else {
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          session.placedTokens.forEachIndexed { index, word ->
            TactileWordChip(
              word = word,
              onClick = { onRemoveToken(index) },
              isPlaced = false
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(36.dp))

    // Word Bank Tokens
    Text(
      text = "WORD BANK",
      fontFamily = NunitoSansFontFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
      letterSpacing = 0.08.sp,
      color = TextSlateSubdued
    )

    Spacer(modifier = Modifier.height(12.dp))

    FlowRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      drill.wordBank.forEach { word ->
        val countInPlaced = session.placedTokens.count { it == word }
        val countInBank = drill.wordBank.count { it == word }
        val isAllUsed = countInPlaced >= countInBank

        TactileWordChip(
          word = word,
          onClick = {
            if (!isAllUsed) {
              onAddToken(word)
            }
          },
          isPlaced = isAllUsed,
          enabled = !isAllUsed
        )
      }
    }
  }
}

@Composable
fun MultipleChoiceDrillView(
  drill: Drill,
  session: ActiveLessonSession,
  onSelectOption: (Int) -> Unit,
  onSpeak: (String) -> Unit
) {
  val labels = listOf("A", "B", "C", "D")

  Column {
    PromptCardWithSpeaker(
      prompt = drill.prompt,
      audioText = drill.audioText,
      onSpeak = onSpeak
    )

    Spacer(modifier = Modifier.height(24.dp))

    drill.options.forEachIndexed { index, option ->
      val isSelected = session.selectedOptionIndex == index
      val isCorrect = if (session.isEvaluating) {
        if (isSelected) session.isCorrect else null
      } else null

      TactileSelectableTile(
        text = option,
        indexLabel = labels.getOrElse(index) { "${index + 1}" },
        selected = isSelected,
        isCorrect = isCorrect,
        onClick = { onSelectOption(index) },
        modifier = Modifier.padding(vertical = 6.dp)
      )
    }
  }
}

@Composable
fun ListeningDrillView(
  drill: Drill,
  session: ActiveLessonSession,
  onSelectOption: (Int) -> Unit,
  onSpeak: (String) -> Unit
) {
  val labels = listOf("A", "B", "C", "D")

  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    // Large Audio Play Card
    TactileCard(
      modifier = Modifier.fillMaxWidth(),
      shapeRadius = 24.dp
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(76.dp)
            .clip(CircleShape)
            .background(CyanAccent)
            .clickable {
              drill.audioText?.let { onSpeak(it) }
            },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Play sound",
            tint = Color.White,
            modifier = Modifier.size(42.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Tap to listen to the audio snippet",
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = TextSlateBody
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    drill.options.forEachIndexed { index, option ->
      val isSelected = session.selectedOptionIndex == index
      val isCorrect = if (session.isEvaluating) {
        if (isSelected) session.isCorrect else null
      } else null

      TactileSelectableTile(
        text = option,
        indexLabel = labels.getOrElse(index) { "${index + 1}" },
        selected = isSelected,
        isCorrect = isCorrect,
        onClick = { onSelectOption(index) },
        modifier = Modifier.padding(vertical = 6.dp)
      )
    }
  }
}

@Composable
fun PairMatchingDrillView(
  drill: Drill,
  session: ActiveLessonSession,
  onSelectPrompt: (String) -> Unit,
  onSelectTranslation: (String) -> Unit
) {
  Column {
    Text(
      text = "Match each pair correctly",
      fontFamily = NunitoSansFontFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = TextSlateHeadline
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      // English Column
      Column(modifier = Modifier.weight(1f)) {
        drill.pairs.forEach { pair ->
          val isMatched = session.matchedPairKeys.contains(pair.prompt)
          val isSelected = session.selectedPromptKey == pair.prompt

          TactilePairTile(
            text = pair.prompt,
            isSelected = isSelected,
            isMatched = isMatched,
            onClick = {
              if (!isMatched) onSelectPrompt(pair.prompt)
            },
            modifier = Modifier.padding(vertical = 6.dp)
          )
        }
      }

      // Foreign Column
      Column(modifier = Modifier.weight(1f)) {
        // Shuffle translations view order consistently
        drill.pairs.reversed().forEach { pair ->
          val isMatched = session.matchedPairKeys.contains(pair.translation)
          val isSelected = session.selectedTranslationKey == pair.translation

          TactilePairTile(
            text = pair.translation,
            isSelected = isSelected,
            isMatched = isMatched,
            onClick = {
              if (!isMatched) onSelectTranslation(pair.translation)
            },
            modifier = Modifier.padding(vertical = 6.dp)
          )
        }
      }
    }
  }
}

@Composable
fun TactilePairTile(
  text: String,
  isSelected: Boolean,
  isMatched: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val shape = RoundedCornerShape(16.dp)
  val (bgColor, borderColor, textColor) = when {
    isMatched -> Triple(EmeraldContainer, EmeraldPrimary, EmeraldPrimaryBase)
    isSelected -> Triple(Color(0xFFCFFAFE), CyanAccent, Color(0xFF155E75))
    else -> Triple(SurfaceCard, BorderSlate, TextSlateHeadline)
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(60.dp)
      .clip(shape)
      .background(if (isMatched) EmeraldPrimaryBase else BorderSlate)
      .clickable(enabled = !isMatched, onClick = onClick)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .clip(shape)
        .background(bgColor)
        .border(2.dp, borderColor, shape)
        .padding(horizontal = 12.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = text,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = if (isMatched) EmeraldPrimaryBase else textColor,
        textAlign = TextAlign.Center
      )
    }
  }
}

@Composable
fun FillBlankDrillView(
  drill: Drill,
  session: ActiveLessonSession,
  onSelectOption: (Int) -> Unit
) {
  val parts = drill.sentenceParts ?: Pair("Yo", "español.")
  val selectedWord = session.selectedOptionIndex?.let { drill.options.getOrNull(it) } ?: "_____"

  Column {
    // Sentence card with blank
    TactileCard(
      modifier = Modifier.fillMaxWidth(),
      shapeRadius = 20.dp
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Text(
          text = parts.first,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 20.sp,
          color = TextSlateHeadline
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(EmeraldContainer)
            .border(2.dp, EmeraldPrimary, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = selectedWord,
            fontFamily = NunitoSansFontFamily,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            color = EmeraldPrimaryBase
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = parts.second,
          fontFamily = NunitoSansFontFamily,
          fontWeight = FontWeight.ExtraBold,
          fontSize = 20.sp,
          color = TextSlateHeadline
        )
      }
    }

    Spacer(modifier = Modifier.height(28.dp))

    Text(
      text = "CHOOSE MISSING WORD",
      fontFamily = NunitoSansFontFamily,
      fontWeight = FontWeight.Bold,
      fontSize = 12.sp,
      letterSpacing = 0.08.sp,
      color = TextSlateSubdued
    )

    Spacer(modifier = Modifier.height(12.dp))

    drill.options.forEachIndexed { index, option ->
      TactileSelectableTile(
        text = option,
        indexLabel = "${index + 1}",
        selected = session.selectedOptionIndex == index,
        onClick = { onSelectOption(index) },
        modifier = Modifier.padding(vertical = 6.dp)
      )
    }
  }
}

@Composable
fun PromptCardWithSpeaker(
  prompt: String,
  audioText: String?,
  onSpeak: (String) -> Unit
) {
  TactileCard(
    modifier = Modifier.fillMaxWidth(),
    shapeRadius = 20.dp
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      if (audioText != null) {
        Box(
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(CyanAccent)
            .clickable { onSpeak(audioText) },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VolumeUp,
            contentDescription = "Speak phrase",
            tint = Color.White,
            modifier = Modifier.size(26.dp)
          )
        }
        Spacer(modifier = Modifier.width(14.dp))
      }

      Text(
        text = prompt,
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        color = TextSlateHeadline
      )
    }
  }
}

@Composable
fun LessonCompletedCelebration(
  xpGained: Int,
  mistakesCount: Int,
  onFinish: () -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(CanvasBackground)
      .padding(24.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 80.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      // Owl mascot asset
      Image(
        painter = painterResource(id = R.drawable.ic_app_mascot),
        contentDescription = "Mascot Owl",
        modifier = Modifier
          .size(160.dp)
          .clip(CircleShape)
          .border(4.dp, EmeraldPrimary, CircleShape),
        contentScale = ContentScale.Crop
      )

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Lesson Complete!",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp,
        letterSpacing = (-0.02).sp,
        color = EmeraldPrimaryBase,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = if (mistakesCount == 0) "Flawless score! You're on fire!" else "Great job practicing today!",
        fontFamily = NunitoSansFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = TextSlateSubdued,
        textAlign = TextAlign.Center
      )

      Spacer(modifier = Modifier.height(28.dp))

      // Reward Stats Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // XP gained card
        TactileCard(
          modifier = Modifier.weight(1f),
          shapeRadius = 18.dp,
          backgroundColor = Color(0xFFFEF3C7)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "TOTAL XP", fontFamily = NunitoSansFontFamily, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = AmberStreakBase)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(text = "⚡", fontSize = 18.sp)
              Spacer(modifier = Modifier.width(4.dp))
              Text(text = "+$xpGained", fontFamily = NunitoSansFontFamily, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = AmberStreakBase)
            }
          }
        }

        // Accuracy card
        TactileCard(
          modifier = Modifier.weight(1f),
          shapeRadius = 18.dp,
          backgroundColor = EmeraldContainer
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "ACCURACY", fontFamily = NunitoSansFontFamily, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = EmeraldPrimaryBase)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = if (mistakesCount == 0) "100%" else "${(100 - mistakesCount * 15).coerceAtLeast(60)}%",
              fontFamily = NunitoSansFontFamily,
              fontWeight = FontWeight.ExtraBold,
              fontSize = 22.sp,
              color = EmeraldPrimaryBase
            )
          }
        }
      }
    }

    TactileButton(
      onClick = onFinish,
      text = "CONTINUE",
      variant = TactileButtonVariant.PRIMARY,
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth(),
      testTag = "lesson_complete_continue_btn"
    )
  }
}
