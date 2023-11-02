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