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
package com.nickwelna.meadbrewerscompanion.calculators

import com.google.common.truth.Truth
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcohol.InputUnit
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcohol.OutputUnit
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcohol.calcPotentialAlcohol
import org.junit.Test

class PotentialAlcoholTest {

    @Test
    fun calcPotentialAlcohol_withSpecificGravityAndABV_returnsCorrectValue() {
        val originalGravity = 1.092f
        val finalGravity = 1.000f
        val inputUnit: InputUnit = InputUnit.SG
        val outputUnit: OutputUnit = OutputUnit.ABV
        val expected = "12.22"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun calcPotentialAlcohol_withSpecificGravityAndABW_returnsCorrectValue() {
        val originalGravity = 1.092f
        val finalGravity = 1.000f
        val inputUnit: InputUnit = InputUnit.SG
        val outputUnit: OutputUnit = OutputUnit.ABW
        val expected = "9.773"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun calcPotentialAlcohol_withBrixAndABV_returnsCorrectValue() {
        val originalGravity = 22.00f
        val finalGravity = 00.00f
        val inputUnit: InputUnit = InputUnit.BRIX
        val outputUnit: OutputUnit = OutputUnit.ABV
        val expected = "12.22"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun calcPotentialAlcohol_withBrixAndABW_returnsCorrectValue() {
        val originalGravity = 22.00f
        val finalGravity = 0f
        val inputUnit: InputUnit = InputUnit.BRIX
        val outputUnit: OutputUnit = OutputUnit.ABW
        val expected = "9.778"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun calcPotentialAlcohol_withBaumeAndABV_returnsCorrectValue() {
        val originalGravity = 12.22f
        val finalGravity = 00.00f
        val inputUnit: InputUnit = InputUnit.BAUME
        val outputUnit: OutputUnit = OutputUnit.ABV
        val expected = "12.22"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun calcPotentialAlcohol_withBaumeAndABW_returnsCorrectValue() {
        val originalGravity = 12.22f
        val finalGravity = 0f
        val inputUnit: InputUnit = InputUnit.BAUME
        val outputUnit: OutputUnit = OutputUnit.ABW
        val expected = "9.776"
        val actual: String = "%.4g".format(
            calcPotentialAlcohol(
                originalGravity, finalGravity, inputUnit, outputUnit
            )
        )
        Truth.assertThat(actual).isEqualTo(expected)
    }
}