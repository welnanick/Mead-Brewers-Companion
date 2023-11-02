/**
 * Mead Brewer's Companion
 * Copyright (C) 2023  Nicholas Welna
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact: https://github.com/welnanick/Mead-Brewers-Companion
 */
package com.nickwelna.meadbrewerscompanion.ui.screens

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nickwelna.meadbrewerscompanion.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PotentialAlcoholScreenTest {

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

        composeTestRule.onNodeWithContentDescription("Original BRIX").performTextInput("22.00")
        composeTestRule.onNodeWithContentDescription("Final BRIX").performTextInput("00.00")
        composeTestRule.onNodeWithText("Calculate ABV").performClick()

        composeTestRule.onNodeWithText("12.22%")
            .assertExists("Potential Alcohol Calculation Incorrect")
    }

    @Test
    fun calculateABW_withBRIX() {

        composeTestRule.onNodeWithText("Specific Gravity").performClick()
        composeTestRule.onNodeWithText("BRIX").performClick()

        composeTestRule.onNodeWithContentDescription("Original BRIX").performTextInput("22.00")
        composeTestRule.onNodeWithContentDescription("Final BRIX").performTextInput("00.00")

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