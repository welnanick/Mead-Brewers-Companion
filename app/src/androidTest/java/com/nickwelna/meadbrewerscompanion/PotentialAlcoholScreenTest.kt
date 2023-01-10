package com.nickwelna.meadbrewerscompanion

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) class PotentialAlcoholScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun calculateABV_withSpecificGravity() {

        composeTestRule.onNodeWithContentDescription("Original Gravity").performTextInput("1.092")
        composeTestRule.onNodeWithContentDescription("Final Gravity").performTextInput("1.000")
        composeTestRule.onNodeWithText("Calculate ABV").performClick()

        composeTestRule.onNodeWithText("12.22%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABW_withSpecificGravity() {

        composeTestRule.onNodeWithContentDescription("Original Gravity").performTextInput("1.092")
        composeTestRule.onNodeWithContentDescription("Final Gravity").performTextInput("1.000")
        composeTestRule.onNodeWithText("ABW").performClick()
        composeTestRule.onNodeWithText("Calculate ABW").performClick()

        composeTestRule.onNodeWithText("9.773%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABV_withBRIX() {

        composeTestRule.onNodeWithText("Specific Gravity").performClick()
        composeTestRule.onNodeWithText("BRIX").performClick()

        composeTestRule.onNodeWithContentDescription("Original Brix").performTextInput("22.00")
        composeTestRule.onNodeWithContentDescription("Final Brix").performTextInput("00.00")
        composeTestRule.onNodeWithText("Calculate ABV").performClick()

        composeTestRule.onNodeWithText("12.22%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABW_withBRIX() {

        composeTestRule.onNodeWithText("Specific Gravity").performClick()
        composeTestRule.onNodeWithText("BRIX").performClick()

        composeTestRule.onNodeWithContentDescription("Original Brix").performTextInput("22.00")
        composeTestRule.onNodeWithContentDescription("Final Brix").performTextInput("00.00")

        composeTestRule.onNodeWithText("ABW").performClick()
        composeTestRule.onNodeWithText("Calculate ABW").performClick()

        composeTestRule.onNodeWithText("9.778%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABV_withBaume() {

        composeTestRule.onNodeWithText("Specific Gravity").performClick()
        composeTestRule.onNodeWithText("Baume").performClick()

        composeTestRule.onNodeWithContentDescription("Original Baume").performTextInput("12.22")
        composeTestRule.onNodeWithContentDescription("Final Baume").performTextInput("00.00")
        composeTestRule.onNodeWithText("Calculate ABV").performClick()

        composeTestRule.onNodeWithText("12.22%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABW_withBaume() {

        composeTestRule.onNodeWithText("Specific Gravity").performClick()
        composeTestRule.onNodeWithText("Baume").performClick()

        composeTestRule.onNodeWithContentDescription("Original Baume").performTextInput("12.22")
        composeTestRule.onNodeWithContentDescription("Final Baume").performTextInput("00.00")

        composeTestRule.onNodeWithText("ABW").performClick()
        composeTestRule.onNodeWithText("Calculate ABW").performClick()

        composeTestRule.onNodeWithText("9.776%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }
}