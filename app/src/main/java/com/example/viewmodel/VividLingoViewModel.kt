package com.example.viewmodel

import android.app.Application
import android.speech.tts.TextToSpeech
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CurriculumData
import com.example.model.DailyQuest
import com.example.model.Drill
import com.example.model.DrillType
import com.example.model.Language
import com.example.model.LeaderboardUser
import com.example.model.LearningUnit
import com.example.model.LessonNode
import com.example.model.ShopItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

enum class AppTab {
  LEARN,
  PRACTICE,
  LEADERBOARD,
  QUESTS,
  PROFILE
}

data class ActiveLessonSession(
  val node: LessonNode,
  val currentDrillIndex: Int = 0,
  val selectedOptionIndex: Int? = null,
  val placedTokens: List<String> = emptyList(),
  val matchedPairKeys: Set<String> = emptySet(),
  val selectedPromptKey: String? = null,
  val selectedTranslationKey: String? = null,
  val isEvaluating: Boolean = false,
  val isCorrect: Boolean? = null,
  val explanation: String? = null,
  val isCompleted: Boolean = false,
  val xpGained: Int = 0,
  val mistakesCount: Int = 0
)

data class VividLingoUiState(
  val currentLanguage: Language = CurriculumData.supportedLanguages[0],
  val languages: List<Language> = CurriculumData.supportedLanguages,
  val units: List<LearningUnit> = emptyList(),
  val activeTab: AppTab = AppTab.LEARN,
  val streakDays: Int = 7,
  val gems: Int = 420,
  val hearts: Int = 5,
  val maxHearts: Int = 5,
  val totalXp: Int = 945,
  val currentLeague: String = "Emerald League",
  val daysLeftInLeague: Int = 3,
  val quests: List<DailyQuest> = CurriculumData.defaultQuests,
  val leaderboard: List<LeaderboardUser> = CurriculumData.leaderboardUsers,
  val shopItems: List<ShopItem> = CurriculumData.shopItems,
  val selectedNodeModal: LessonNode? = null,
  val activeSession: ActiveLessonSession? = null,
  val showLanguageDialog: Boolean = false,
  val showHeartsDialog: Boolean = false,
  val showStreakDialog: Boolean = false,
  val showChestCelebration: Boolean = false,
  val practiceModeActive: Boolean = false,
  val feedbackToast: String? = null
)

class VividLingoViewModel(application: Application) : AndroidViewModel(application), TextToSpeech.OnInitListener {

  private val _uiState = MutableStateFlow(VividLingoUiState())
  val uiState: StateFlow<VividLingoUiState> = _uiState.asStateFlow()

  private var tts: TextToSpeech? = null
  private var isTtsReady = false

  init {
    tts = TextToSpeech(application.applicationContext, this)
    loadUnitsForLanguage(_uiState.value.currentLanguage.id)
  }

  override fun onInit(status: Int) {
    if (status == TextToSpeech.SUCCESS) {
      isTtsReady = true
      updateTtsLanguage(_uiState.value.currentLanguage.code)
    }
  }

  private fun updateTtsLanguage(langCode: String) {
    if (!isTtsReady || tts == null) return
    val locale = when (langCode) {
      "es" -> Locale("es", "ES")
      "fr" -> Locale.FRENCH
      "ja" -> Locale.JAPANESE
      "de" -> Locale.GERMAN
      "it" -> Locale.ITALIAN
      else -> Locale.ENGLISH
    }
    tts?.language = locale
  }

  fun speak(text: String) {
    if (!isTtsReady || tts == null) return
    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "vivid_speech_${System.currentTimeMillis()}")
  }

  private fun loadUnitsForLanguage(langId: String) {
    val units = CurriculumData.getUnitsForLanguage(langId)
    _uiState.update { it.copy(units = units) }
  }

  fun switchLanguage(lang: Language) {
    updateTtsLanguage(lang.code)
    _uiState.update {
      it.copy(
        currentLanguage = lang,
        units = CurriculumData.getUnitsForLanguage(lang.id),
        showLanguageDialog = false,
        feedbackToast = "Switched to ${lang.name}!"
      )
    }
  }

  fun selectTab(tab: AppTab) {
    _uiState.update { it.copy(activeTab = tab) }
  }

  fun openNodeModal(node: LessonNode) {
    if (node.iconType == com.example.model.LessonIconType.CHEST && !node.isCompleted) {
      // Open chest celebration
      _uiState.update {
        it.copy(
          gems = it.gems + 50,
          totalXp = it.totalXp + 25,
          showChestCelebration = true,
          feedbackToast = "+50 Gems & +25 XP from Treasure Chest!"
        )
      }
      return
    }
    _uiState.update { it.copy(selectedNodeModal = node) }
  }

  fun dismissNodeModal() {
    _uiState.update { it.copy(selectedNodeModal = null) }
  }

  fun dismissChestCelebration() {
    _uiState.update { it.copy(showChestCelebration = false) }
  }

  fun startLesson(node: LessonNode) {
    val drills = if (node.drills.isNotEmpty()) node.drills else CurriculumData.createSpanishCafeDrills()
    val session = ActiveLessonSession(
      node = node.copy(drills = drills),
      currentDrillIndex = 0,
      xpGained = 0
    )
    _uiState.update {
      it.copy(
        activeSession = session,
        selectedNodeModal = null
      )
    }

    // Pronounce audio if first drill has audio
    drills.firstOrNull()?.audioText?.let { speak(it) }
  }

  fun selectMultipleChoiceOption(index: Int) {
    val currentSession = _uiState.value.activeSession ?: return
    if (currentSession.isEvaluating) return
    _uiState.update {
      it.copy(
        activeSession = currentSession.copy(selectedOptionIndex = index)
      )
    }
  }

  fun addWordToken(token: String) {
    val currentSession = _uiState.value.activeSession ?: return
    if (currentSession.isEvaluating) return
    val updatedTokens = currentSession.placedTokens + token
    _uiState.update {
      it.copy(
        activeSession = currentSession.copy(placedTokens = updatedTokens)
      )
    }
  }

  fun removeWordToken(index: Int) {
    val currentSession = _uiState.value.activeSession ?: return
    if (currentSession.isEvaluating) return
    if (index in currentSession.placedTokens.indices) {
      val mutable = currentSession.placedTokens.toMutableList()
      mutable.removeAt(index)
      _uiState.update {
        it.copy(
          activeSession = currentSession.copy(placedTokens = mutable)
        )
      }
    }
  }

  fun selectPairPrompt(prompt: String) {
    val session = _uiState.value.activeSession ?: return
    if (session.isEvaluating) return

    val trans = session.selectedTranslationKey
    if (trans != null) {
      // Check match
      checkPairMatch(prompt, trans)
    } else {
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            selectedPromptKey = if (session.selectedPromptKey == prompt) null else prompt
          )
        )
      }
    }
  }

  fun selectPairTranslation(translation: String) {
    val session = _uiState.value.activeSession ?: return
    if (session.isEvaluating) return

    val prompt = session.selectedPromptKey
    if (prompt != null) {
      // Check match
      checkPairMatch(prompt, translation)
    } else {
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            selectedTranslationKey = if (session.selectedTranslationKey == translation) null else translation
          )
        )
      }
    }
  }

  private fun checkPairMatch(prompt: String, translation: String) {
    val session = _uiState.value.activeSession ?: return
    val currentDrill = session.node.drills.getOrNull(session.currentDrillIndex) ?: return

    val matchedPair = currentDrill.pairs.find { it.prompt == prompt && it.translation == translation }
    if (matchedPair != null) {
      val newMatched = session.matchedPairKeys + prompt + translation
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            matchedPairKeys = newMatched,
            selectedPromptKey = null,
            selectedTranslationKey = null
          )
        )
      }
    } else {
      // Mismatch
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            selectedPromptKey = null,
            selectedTranslationKey = null,
            mistakesCount = session.mistakesCount + 1
          )
        )
      }
    }
  }

  fun checkCurrentAnswer() {
    val session = _uiState.value.activeSession ?: return
    val drill = session.node.drills.getOrNull(session.currentDrillIndex) ?: return

    var isCorrect = false
    var explanation: String? = null

    when (drill.type) {
      DrillType.MULTIPLE_CHOICE, DrillType.LISTENING_DRILL, DrillType.FILL_BLANK -> {
        isCorrect = session.selectedOptionIndex == drill.correctOptionIndex
        explanation = drill.options.getOrNull(drill.correctOptionIndex)
      }
      DrillType.WORD_BANK_TRANSLATION -> {
        val constructed = session.placedTokens.joinToString(" ")
        isCorrect = constructed.trim().equals(drill.correctTranslation?.trim(), ignoreCase = true)
        explanation = drill.correctTranslation
      }
      DrillType.PAIR_MATCHING -> {
        val allMatched = drill.pairs.all {
          session.matchedPairKeys.contains(it.prompt) && session.matchedPairKeys.contains(it.translation)
        }
        isCorrect = allMatched
        explanation = "All pairs matched!"
      }
    }

    if (!isCorrect) {
      val newHearts = (_uiState.value.hearts - 1).coerceAtLeast(0)
      _uiState.update {
        it.copy(
          hearts = newHearts,
          activeSession = session.copy(
            isEvaluating = true,
            isCorrect = false,
            explanation = explanation,
            mistakesCount = session.mistakesCount + 1
          )
        )
      }
    } else {
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            isEvaluating = true,
            isCorrect = true,
            explanation = explanation,
            xpGained = session.xpGained + (session.node.xpReward / session.node.drills.size.coerceAtLeast(1))
          )
        )
      }
    }
  }

  fun continueSession() {
    val session = _uiState.value.activeSession ?: return
    val drills = session.node.drills

    if (session.currentDrillIndex + 1 < drills.size) {
      val nextIndex = session.currentDrillIndex + 1
      _uiState.update {
        it.copy(
          activeSession = session.copy(
            currentDrillIndex = nextIndex,
            selectedOptionIndex = null,
            placedTokens = emptyList(),
            matchedPairKeys = emptySet(),
            selectedPromptKey = null,
            selectedTranslationKey = null,
            isEvaluating = false,
            isCorrect = null,
            explanation = null
          )
        )
      }
      drills.getOrNull(nextIndex)?.audioText?.let { speak(it) }
    } else {
      // Completed all drills in the lesson!
      val totalEarnedXp = session.node.xpReward
      val updatedUnits = _uiState.value.units.map { unit ->
        if (unit.id == session.node.unitId) {
          val updatedNodes = unit.nodes.map { node ->
            if (node.id == session.node.id) {
              node.copy(isCompleted = true, stars = 3, isCurrent = false)
            } else node
          }
          unit.copy(nodes = updatedNodes)
        } else unit
      }

      _uiState.update {
        it.copy(
          units = updatedUnits,
          totalXp = it.totalXp + totalEarnedXp,
          gems = it.gems + 15,
          activeSession = session.copy(
            isCompleted = true,
            xpGained = totalEarnedXp,
            isEvaluating = false
          )
        )
      }
    }
  }

  fun exitLesson() {
    _uiState.update { it.copy(activeSession = null) }
  }

  fun claimQuest(questId: String) {
    _uiState.update { state ->
      val updated = state.quests.map { q ->
        if (q.id == questId && !q.isClaimed && q.currentProgress >= q.maxProgress) {
          q.copy(isClaimed = true)
        } else q
      }
      val claimedQuest = state.quests.find { it.id == questId }
      val gemBonus = if (claimedQuest != null && !claimedQuest.isClaimed) claimedQuest.gemReward else 0
      state.copy(
        quests = updated,
        gems = state.gems + gemBonus,
        feedbackToast = "+$gemBonus Gems claimed!"
      )
    }
  }

  fun buyShopItem(item: ShopItem) {
    val state = _uiState.value
    if (state.gems < item.costGems) {
      _uiState.update { it.copy(feedbackToast = "Not enough gems!") }
      return
    }

    when (item.id) {
      "shop_heart_refill" -> {
        _uiState.update {
          it.copy(
            gems = it.gems - item.costGems,
            hearts = it.maxHearts,
            feedbackToast = "Hearts fully restored (5/5)!"
          )
        }
      }
      "shop_streak_freeze" -> {
        _uiState.update {
          it.copy(
            gems = it.gems - item.costGems,
            feedbackToast = "Streak Freeze activated! Your streak is protected."
          )
        }
      }
      else -> {
        _uiState.update {
          it.copy(
            gems = it.gems - item.costGems,
            feedbackToast = "Purchased ${item.title}!"
          )
        }
      }
    }
  }

  fun toggleLanguageDialog(show: Boolean) {
    _uiState.update { it.copy(showLanguageDialog = show) }
  }

  fun toggleHeartsDialog(show: Boolean) {
    _uiState.update { it.copy(showHeartsDialog = show) }
  }

  fun toggleStreakDialog(show: Boolean) {
    _uiState.update { it.copy(showStreakDialog = show) }
  }

  fun clearFeedbackToast() {
    _uiState.update { it.copy(feedbackToast = null) }
  }

  override fun onCleared() {
    tts?.stop()
    tts?.shutdown()
    super.onCleared()
  }
}
