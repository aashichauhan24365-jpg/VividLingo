package com.example

import com.example.data.CurriculumData
import com.example.model.DrillType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun testCurriculumDataLoadsCorrectly() {
    val languages = CurriculumData.supportedLanguages
    assertTrue("Should support multiple languages", languages.size >= 4)

    val spanishUnits = CurriculumData.getUnitsForLanguage("es")
    assertFalse("Spanish curriculum should have units", spanishUnits.isEmpty())

    val unit1 = spanishUnits[0]
    assertEquals(1, unit1.number)
    assertTrue("Unit 1 should contain lesson nodes", unit1.nodes.isNotEmpty())

    val firstNode = unit1.nodes[0]
    assertTrue("First node should have drills", firstNode.drills.isNotEmpty())
  }

  @Test
  fun testSpanishDrillsIntegrity() {
    val drills = CurriculumData.createSpanishCafeDrills()
    assertTrue("Cafe drills should have multiple exercises", drills.size >= 4)

    val wordBankDrill = drills.find { it.type == DrillType.WORD_BANK_TRANSLATION }
    assertNotNull("Should have word bank drill", wordBankDrill)
    assertTrue("Word bank should contain tokens", wordBankDrill!!.wordBank.isNotEmpty())
    assertNotNull("Should have correct translation", wordBankDrill.correctTranslation)

    val multipleChoiceDrill = drills.find { it.type == DrillType.MULTIPLE_CHOICE }
    assertNotNull("Should have multiple choice drill", multipleChoiceDrill)
    assertTrue("Correct index should be in bounds", multipleChoiceDrill!!.correctOptionIndex in multipleChoiceDrill.options.indices)
  }

  @Test
  fun testShopAndQuestsData() {
    val quests = CurriculumData.defaultQuests
    assertTrue("Should have daily quests", quests.isNotEmpty())

    val shopItems = CurriculumData.shopItems
    assertTrue("Should have shop items", shopItems.isNotEmpty())
    assertNotNull("Should have heart refill item", shopItems.find { it.id == "shop_heart_refill" })
  }
}
