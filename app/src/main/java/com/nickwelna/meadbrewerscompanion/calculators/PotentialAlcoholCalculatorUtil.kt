package com.nickwelna.meadbrewerscompanion.calculators

import logcat.LogPriority
import logcat.logcat

object PotentialAlcoholCalculatorUtil {

    fun calcPotentialAlcohol(
        originalMeasurement: Float,
        finalMeasurement: Float,
        inputUnit: InputUnit,
        outputUnit: OutputUnit
    ): Float {
        logcat(LogPriority.DEBUG) { "Input params: $originalMeasurement, $finalMeasurement, $inputUnit, $outputUnit" }
        return when (outputUnit) {
            OutputUnit.ABV -> calcPotentialAlcoholByVolume(
                originalMeasurement,
                finalMeasurement,
                inputUnit
            )

            // ABV * 0.8 = ABW
            OutputUnit.ABW -> 0.8f * calcPotentialAlcoholByVolume(
                originalMeasurement,
                finalMeasurement,
                inputUnit
            )
        }
    }

    private fun calcPotentialAlcoholByVolume(
        originalMeasurement: Float,
        finalMeasurement: Float,
        unit: InputUnit
    ): Float {
        return when (unit) {
            InputUnit.SG -> specificGravityToAlcoholByVolume(1 + originalMeasurement - finalMeasurement)
            InputUnit.BRIX -> (originalMeasurement - finalMeasurement) / 1.8f // 1.8 BRIX = 1 BAUME
            InputUnit.BAUME -> originalMeasurement - finalMeasurement // BAUME is ABV, roughly
        }
    }

    private fun specificGravityToAlcoholByVolume(specificGravity: Float): Float {
        return specificGravityToBaume(specificGravity)
    }

    private fun specificGravityToBaume(specificGravity: Float): Float {
        return 145 * (1 - 1 / specificGravity)
    }

    enum class InputUnit(private val dropdownIndex: Int) {

        SG(0),
        BRIX(1),
        BAUME(2);

        companion object {
            fun fromIndex(index: Int): InputUnit {
                return when (index) {
                    SG.dropdownIndex -> SG
                    BRIX.dropdownIndex -> BRIX
                    BAUME.dropdownIndex -> BAUME
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }

            fun getUnitLabelString(index: Int): String {
                return when (index) {
                    SG.dropdownIndex -> "Gravity"
                    BRIX.dropdownIndex -> "Brix"
                    BAUME.dropdownIndex -> "Baume"
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }
        }
    }

    enum class OutputUnit(private val radioIndex: Int) {
        ABV(0),
        ABW(1);

        companion object {
            fun fromIndex(index: Int): OutputUnit {
                return when (index) {
                    ABV.radioIndex -> ABV
                    ABW.radioIndex -> ABW
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }

            fun getUnitLabelString(index: Int): String {
                return when (index) {
                    ABV.radioIndex -> "ABV"
                    ABW.radioIndex -> "ABW"
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }
        }
    }
}