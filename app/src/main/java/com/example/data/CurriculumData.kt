package com.example.data

import com.example.model.DailyQuest
import com.example.model.Drill
import com.example.model.DrillType
import com.example.model.Language
import com.example.model.LeaderboardUser
import com.example.model.LearningUnit
import com.example.model.LessonIconType
import com.example.model.LessonNode
import com.example.model.MatchingPair
import com.example.model.ShopItem

object CurriculumData {

  val supportedLanguages = listOf(
    Language("es", "Spanish", "Español", "🇪🇸", "34.2M learners", "es"),
    Language("fr", "French", "Français", "🇫🇷", "21.8M learners", "fr"),
    Language("ja", "Japanese", "日本語", "🇯🇵", "16.4M learners", "ja"),
    Language("de", "German", "Deutsch", "🇩🇪", "12.1M learners", "de"),
    Language("it", "Italian", "Italiano", "🇮🇹", "8.9M learners", "it")
  )

  fun getUnitsForLanguage(langCode: String): List<LearningUnit> {
    return when (langCode) {
      "es" -> spanishUnits
      "fr" -> frenchUnits
      "ja" -> japaneseUnits
      else -> spanishUnits
    }
  }

  private val spanishUnits = listOf(
    LearningUnit(
      id = 1,
      number = 1,
      title = "Unit 1: First Steps",
      description = "Order food, greet people, and introduce yourself",
      bannerColorHex = 0xFF10B981,
      nodes = listOf(
        LessonNode(
          id = "es_1_1",
          unitId = 1,
          title = "Greetings",
          subtitle = "Say hello & introduce yourself",
          isCompleted = true,
          stars = 3,
          isCurrent = false,
          isLocked = false,
          iconType = LessonIconType.GREETING,
          drills = createSpanishGreetingsDrills()
        ),
        LessonNode(
          id = "es_1_2",
          unitId = 1,
          title = "Basic Phrases",
          subtitle = "Polite words and essential manners",
          isCompleted = true,
          stars = 3,
          isCurrent = false,
          isLocked = false,
          iconType = LessonIconType.BOOK,
          drills = createSpanishPhrasesDrills()
        ),
        LessonNode(
          id = "es_1_3",
          unitId = 1,
          title = "Café Orders",
          subtitle = "Coffee, tea, and quick bites",
          isCompleted = false,
          stars = 0,
          isCurrent = true,
          isLocked = false,
          iconType = LessonIconType.FOOD,
          drills = createSpanishCafeDrills()
        ),
        LessonNode(
          id = "es_1_4",
          unitId = 1,
          title = "Mid-Unit Chest",
          subtitle = "Claim bonus gems & XP boost",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.CHEST
        ),
        LessonNode(
          id = "es_1_5",
          unitId = 1,
          title = "Getting Around",
          subtitle = "Ask for directions in town",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.TRAVEL,
          drills = createSpanishTravelDrills()
        ),
        LessonNode(
          id = "es_1_6",
          unitId = 1,
          title = "Unit 1 Trophy Quiz",
          subtitle = "Prove your mastery of the basics",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.TROPHY
        )
      )
    ),
    LearningUnit(
      id = 2,
      number = 2,
      title = "Unit 2: Daily Routines",
      description = "Talk about habits, time, and activities",
      bannerColorHex = 0xFF06B6D4,
      nodes = listOf(
        LessonNode(
          id = "es_2_1",
          unitId = 2,
          title = "Morning Habits",
          subtitle = "Waking up and breakfast",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.FOOD
        ),
        LessonNode(
          id = "es_2_2",
          unitId = 2,
          title = "Telling Time",
          subtitle = "Schedules and meetings",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.GRAMMAR
        ),
        LessonNode(
          id = "es_2_3",
          unitId = 2,
          title = "Weekend Fun",
          subtitle = "Hobbies, music, and sports",
          isCompleted = false,
          stars = 0,
          isCurrent = false,
          isLocked = true,
          iconType = LessonIconType.BOOK
        )
      )
    )
  )

  private val frenchUnits = listOf(
    LearningUnit(
      id = 1,
      number = 1,
      title = "Unité 1: Les Bases",
      description = "Salutations et conversations de base",
      bannerColorHex = 0xFF10B981,
      nodes = listOf(
        LessonNode(
          id = "fr_1_1",
          unitId = 1,
          title = "Bonjour!",
          subtitle = "Greetings & pleasantries",
          isCompleted = true,
          stars = 3,
          isCurrent = false,
          isLocked = false,
          iconType = LessonIconType.GREETING,
          drills = createFrenchGreetingsDrills()
        ),
        LessonNode(
          id = "fr_1_2",
          unitId = 1,
          title = "Au Bistro",
          subtitle = "Order coffee and croissants",
          isCompleted = false,
          stars = 0,
          isCurrent = true,
          isLocked = false,
          iconType = LessonIconType.FOOD,
          drills = createFrenchCafeDrills()
        )
      )
    )
  )

  private val japaneseUnits = listOf(
    LearningUnit(
      id = 1,
      number = 1,
      title = "Unit 1: Hiragana & Greetings",
      description = "Basic greetings and polite expressions",
      bannerColorHex = 0xFFF59E0B,
      nodes = listOf(
        LessonNode(
          id = "ja_1_1",
          unitId = 1,
          title = "Konnichiwa",
          subtitle = "Essential Japanese greetings",
          isCompleted = true,
          stars = 3,
          isCurrent = false,
          isLocked = false,
          iconType = LessonIconType.GREETING,
          drills = createJapaneseGreetingsDrills()
        ),
        LessonNode(
          id = "ja_1_2",
          unitId = 1,
          title = "In Tokyo",
          subtitle = "Ordering green tea & ramen",
          isCompleted = false,
          stars = 0,
          isCurrent = true,
          isLocked = false,
          iconType = LessonIconType.FOOD,
          drills = createJapaneseGreetingsDrills()
        )
      )
    )
  )

  fun createSpanishGreetingsDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "sg_1",
        type = DrillType.MULTIPLE_CHOICE,
        instruction = "Select the correct translation",
        prompt = "How do you say 'Good morning'?",
        audioText = "Buenos días",
        options = listOf("Buenos días", "Buenas noches", "Hasta luego", "Por favor"),
        correctOptionIndex = 0,
        tip = "Used until afternoon!"
      ),
      Drill(
        id = "sg_2",
        type = DrillType.WORD_BANK_TRANSLATION,
        instruction = "Translate this sentence",
        prompt = "Hello, nice to meet you",
        audioText = "Hola mucho gusto",
        wordBank = listOf("Hola", "adiós", "mucho", "gusto", "gracias", "leche"),
        correctTranslation = "Hola mucho gusto"
      ),
      Drill(
        id = "sg_3",
        type = DrillType.PAIR_MATCHING,
        instruction = "Tap the matching pairs",
        prompt = "Match the English and Spanish words",
        pairs = listOf(
          MatchingPair("Hello", "Hola"),
          MatchingPair("Goodbye", "Adiós"),
          MatchingPair("Please", "Por favor"),
          MatchingPair("Thank you", "Gracias")
        )
      )
    )
  }

  fun createSpanishPhrasesDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "sp_1",
        type = DrillType.LISTENING_DRILL,
        instruction = "Listen and pick the correct meaning",
        prompt = "Listen to the Spanish audio",
        audioText = "Muchas gracias por tu ayuda",
        options = listOf(
          "Thank you very much for your help",
          "Where is the nearest restaurant?",
          "I would like to order two coffees",
          "See you tomorrow afternoon"
        ),
        correctOptionIndex = 0
      ),
      Drill(
        id = "sp_2",
        type = DrillType.FILL_BLANK,
        instruction = "Complete the sentence",
        prompt = "Yo _____ inglés y español.",
        sentenceParts = Pair("Yo", "inglés y español."),
        options = listOf("hablo", "como", "bebo", "duermo"),
        missingWord = "hablo",
        correctOptionIndex = 0
      )
    )
  }

  fun createSpanishCafeDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "sc_1",
        type = DrillType.WORD_BANK_TRANSLATION,
        instruction = "Translate this sentence",
        prompt = "A coffee with milk, please",
        audioText = "Un café con leche por favor",
        wordBank = listOf("Un", "café", "con", "leche", "por", "favor", "té", "agua", "pan"),
        correctTranslation = "Un café con leche por favor",
        tip = "'Con leche' means with milk!"
      ),
      Drill(
        id = "sc_2",
        type = DrillType.MULTIPLE_CHOICE,
        instruction = "What does this mean?",
        prompt = "¿La cuenta, por favor?",
        audioText = "La cuenta por favor",
        options = listOf(
          "The check, please?",
          "Where is the table?",
          "Is the coffee hot?",
          "Can I have more water?"
        ),
        correctOptionIndex = 0
      ),
      Drill(
        id = "sc_3",
        type = DrillType.PAIR_MATCHING,
        instruction = "Tap the matching pairs",
        prompt = "Match foods and drinks",
        pairs = listOf(
          MatchingPair("Coffee", "Café"),
          MatchingPair("Tea", "Té"),
          MatchingPair("Water", "Agua"),
          MatchingPair("Bread", "Pan")
        )
      ),
      Drill(
        id = "sc_4",
        type = DrillType.LISTENING_DRILL,
        instruction = "Listen and choose what was ordered",
        prompt = "Café soundbite",
        audioText = "Dos vasos de agua fría",
        options = listOf(
          "Two glasses of cold water",
          "Two hot cups of espresso",
          "One slice of chocolate cake",
          "A bottle of fresh apple juice"
        ),
        correctOptionIndex = 0
      ),
      Drill(
        id = "sc_5",
        type = DrillType.WORD_BANK_TRANSLATION,
        instruction = "Translate this sentence",
        prompt = "I want a sandwich",
        audioText = "Yo quiero un sándwich",
        wordBank = listOf("Yo", "quiero", "un", "sándwich", "bebe", "manzana", "mesa"),
        correctTranslation = "Yo quiero un sándwich"
      )
    )
  }

  fun createSpanishTravelDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "st_1",
        type = DrillType.MULTIPLE_CHOICE,
        instruction = "Select the translation",
        prompt = "¿Dónde está el hotel?",
        audioText = "¿Dónde está el hotel?",
        options = listOf("Where is the hotel?", "Is the hotel open?", "Where is the airport?", "I like this hotel"),
        correctOptionIndex = 0
      )
    )
  }

  fun createFrenchGreetingsDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "fg_1",
        type = DrillType.MULTIPLE_CHOICE,
        instruction = "Select the translation",
        prompt = "How do you say 'Good morning / Hello'?",
        audioText = "Bonjour",
        options = listOf("Bonjour", "Bonsoir", "Merci", "S'il vous plaît"),
        correctOptionIndex = 0
      ),
      Drill(
        id = "fg_2",
        type = DrillType.WORD_BANK_TRANSLATION,
        instruction = "Translate this phrase",
        prompt = "Thank you very much",
        audioText = "Merci beaucoup",
        wordBank = listOf("Merci", "beaucoup", "bonjour", "salut", "s'il", "chat"),
        correctTranslation = "Merci beaucoup"
      )
    )
  }

  fun createFrenchCafeDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "fc_1",
        type = DrillType.WORD_BANK_TRANSLATION,
        instruction = "Translate this sentence",
        prompt = "A croissant, please",
        audioText = "Un croissant s'il vous plaît",
        wordBank = listOf("Un", "croissant", "s'il", "vous", "plaît", "café", "eau"),
        correctTranslation = "Un croissant s'il vous plaît"
      )
    )
  }

  fun createJapaneseGreetingsDrills(): List<Drill> {
    return listOf(
      Drill(
        id = "jg_1",
        type = DrillType.MULTIPLE_CHOICE,
        instruction = "Choose the greeting",
        prompt = "Konnichiwa (こんにちは)",
        audioText = "こんにちは",
        options = listOf("Hello / Good afternoon", "Good night", "Thank you", "Goodbye"),
        correctOptionIndex = 0
      ),
      Drill(
        id = "jg_2",
        type = DrillType.PAIR_MATCHING,
        instruction = "Match the greetings",
        prompt = "Match Japanese with English",
        pairs = listOf(
          MatchingPair("Hello", "Konnichiwa"),
          MatchingPair("Thank you", "Arigatou"),
          MatchingPair("Goodbye", "Sayounara"),
          MatchingPair("Yes", "Hai")
        )
      )
    )
  }

  val defaultQuests = listOf(
    DailyQuest("q_1", "Earn 30 XP in lessons", 15, 30, 20),
    DailyQuest("q_2", "Complete 2 lessons today", 1, 2, 15),
    DailyQuest("q_3", "Match 8 word pairs without errors", 4, 8, 25),
    DailyQuest("q_4", "Maintain a 7-day streak", 7, 7, 50, isClaimed = true)
  )

  val leaderboardUsers = listOf(
    LeaderboardUser(1, "Sofia Garcia", "avatar_1", 1420, isPromoted = true),
    LeaderboardUser(2, "Kenji Sato", "avatar_2", 1290, isPromoted = true),
    LeaderboardUser(3, "Elena Rostova", "avatar_3", 1180, isPromoted = true),
    LeaderboardUser(4, "You", "avatar_user", 945, isCurrentUser = true, isPromoted = true),
    LeaderboardUser(5, "Lucas Dubois", "avatar_4", 890),
    LeaderboardUser(6, "Amara Okafor", "avatar_5", 810),
    LeaderboardUser(7, "Mateo Silva", "avatar_6", 750),
    LeaderboardUser(8, "Chloe Chen", "avatar_7", 690),
    LeaderboardUser(9, "Liam O'Connor", "avatar_8", 640),
    LeaderboardUser(10, "Zoe Miller", "avatar_9", 580)
  )

  val shopItems = listOf(
    ShopItem(
      id = "shop_heart_refill",
      title = "Refill Hearts",
      description = "Restore your hearts to maximum (5/5) instantly.",
      costGems = 150,
      iconEmoji = "❤️"
    ),
    ShopItem(
      id = "shop_streak_freeze",
      title = "Streak Freeze",
      description = "Protects your streak if you miss practicing for one full day.",
      costGems = 200,
      iconEmoji = "🧊"
    ),
    ShopItem(
      id = "shop_double_xp",
      title = "Double XP Boost",
      description = "Earn 2x XP points for the next 30 minutes of lessons.",
      costGems = 100,
      iconEmoji = "⚡"
    ),
    ShopItem(
      id = "shop_mascot_hat",
      title = "Golden Scholar Cap",
      description = "Dress your owl mascot in a celebratory graduation cap.",
      costGems = 300,
      iconEmoji = "🎓"
    )
  )
}
