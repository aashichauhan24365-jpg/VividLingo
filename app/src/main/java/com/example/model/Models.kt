package com.example.model

enum class DrillType {
  WORD_BANK_TRANSLATION,
  MULTIPLE_CHOICE,
  PAIR_MATCHING,
  LISTENING_DRILL,
  FILL_BLANK
}

data class WordToken(
  val id: String,
  val text: String,
  val isPunctuation: Boolean = false
)

data class MatchingPair(
  val prompt: String,
  val translation: String
)

data class Drill(
  val id: String,
  val type: DrillType,
  val instruction: String,
  val prompt: String,
  val audioText: String? = null,
  val options: List<String> = emptyList(),
  val wordBank: List<String> = emptyList(),
  val correctTranslation: String? = null,
  val correctOptionIndex: Int = -1,
  val pairs: List<MatchingPair> = emptyList(),
  val missingWord: String? = null,
  val sentenceParts: Pair<String, String>? = null, // for fill blank before/after
  val tip: String? = null
)

data class LessonNode(
  val id: String,
  val unitId: Int,
  val title: String,
  val subtitle: String,
  val xpReward: Int = 15,
  val isCompleted: Boolean = false,
  val stars: Int = 0, // 0 to 3
  val isCurrent: Boolean = false,
  val isLocked: Boolean = false,
  val iconType: LessonIconType = LessonIconType.BOOK,
  val drills: List<Drill> = emptyList()
)

enum class LessonIconType {
  GREETING,
  FOOD,
  TRAVEL,
  SHOPPING,
  CULTURE,
  GRAMMAR,
  BOOK,
  CHEST,
  TROPHY
}

data class LearningUnit(
  val id: Int,
  val number: Int,
  val title: String,
  val description: String,
  val bannerColorHex: Long = 0xFF10B981,
  val nodes: List<LessonNode> = emptyList()
)

data class Language(
  val id: String,
  val name: String,
  val nativeName: String,
  val flagEmoji: String,
  val learnersCount: String,
  val code: String // for TTS e.g. "es", "fr", "ja", "de"
)

data class DailyQuest(
  val id: String,
  val title: String,
  val currentProgress: Int,
  val maxProgress: Int,
  val gemReward: Int,
  val isClaimed: Boolean = false
)

data class LeaderboardUser(
  val rank: Int,
  val name: String,
  val avatarSeed: String,
  val xp: Int,
  val isCurrentUser: Boolean = false,
  val isPromoted: Boolean = false
)

data class ShopItem(
  val id: String,
  val title: String,
  val description: String,
  val costGems: Int,
  val iconEmoji: String,
  val isBought: Boolean = false
)
