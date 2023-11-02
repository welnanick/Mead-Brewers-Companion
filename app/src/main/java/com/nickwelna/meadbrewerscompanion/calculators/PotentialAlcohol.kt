package com.nickwelna.meadbrewerscompanion.calculators

import android.content.Context
import com.nickwelna.meadbrewerscompanion.R
import logcat.LogPriority
import logcat.logcat

/**
 * An object to hold functions related to the calculation of potential alcohol in a brew.
 *
 * An object was used so the the logcat logger will auto-generate a tag rather than requiring one to
 * be passed in. Otherwise, these methods could be declared at the top level in this file.
 */
object PotentialAlcohol {

    /**
     * Calculates the potential alcohol using the inputted measurements and units.
     *
     * @param originalMeasurement The initial measurement taken before fermentation.
     * @param finalMeasurement The final measurement taken after fermentation.
     * @param inputUnit The unit of measurement used to take [originalMeasurement] and [finalMeasurement].
     * @param outputUnit The unit of measurement that should be used to represent the result.
     *
     * @return the potential alcohol calculated, using [outputUnit] as the unit of measure.
     */
    fun calcPotentialAlcohol(
        originalMeasurement: Float,
        finalMeasurement: Float,
        inputUnit: InputUnit,
        outputUnit: OutputUnit
    ): Float {
        logcat(LogPriority.INFO) { "#calcPotentialAlcohol" }
        logcat(LogPriority.DEBUG) { "Input params: $originalMeasurement, $finalMeasurement, $inputUnit, $outputUnit" }
        return when (outputUnit) {
            OutputUnit.ABV -> calcPotentialAlcoholByVolume(
                originalMeasurement, finalMeasurement, inputUnit
            )

            // ABV * 0.8 = ABW
            OutputUnit.ABW -> 0.8f * calcPotentialAlcoholByVolume(
                originalMeasurement, finalMeasurement, inputUnit
            )
        }
    }

    /**
     * Calculates the alcohol by volume using the inputted measurements and units.
     *
     * @param originalMeasurement The initial measurement taken before fermentation.
     * @param finalMeasurement The final measurement taken after fermentation.
     * @param unit The unit of measurement used to take [originalMeasurement] and [finalMeasurement].
     *
     * @return the potential alcohol by volume calculated.
     */
    private fun calcPotentialAlcoholByVolume(
        originalMeasurement: Float, finalMeasurement: Float, unit: InputUnit
    ): Float {
        logcat(LogPriority.INFO) { "#calcPotentialAlcoholByVolume" }
        return when (unit) {
            InputUnit.SG -> specificGravityToAlcoholByVolume(1 + originalMeasurement - finalMeasurement)
            InputUnit.BRIX -> (originalMeasurement - finalMeasurement) / 1.8f // 1.8 BRIX = 1 BAUME
            InputUnit.BAUME -> originalMeasurement - finalMeasurement // BAUME is ABV, roughly
        }
    }

    /**
     * Converts a specific gravity measurement to alcohol by volume.
     *
     * @param specificGravity the specific gravity measurement to convert.
     *
     * @return The equivalent alcohol by volume of [specificGravity].
     */
    private fun specificGravityToAlcoholByVolume(specificGravity: Float): Float {
        logcat(LogPriority.INFO) { "#specificGravityToAlcoholByVolume" }
        return specificGravityToBaume(specificGravity)
    }

    /**
     * Converts a specific gravity measurement to baume.
     *
     * @param specificGravity the specific gravity measurement to convert.
     *
     * @return The equivalent baume of [specificGravity].
     */
    private fun specificGravityToBaume(specificGravity: Float): Float {
        logcat(LogPriority.INFO) { "#specificGravityToBaume" }
        return 145 * (1 - 1 / specificGravity)
    }

    /**
     * An enum to represent the different input units available.
     */
    enum class InputUnit(private val dropdownIndex: Int) {

        SG(0), BRIX(1), BAUME(2);

        companion object {
            fun fromIndex(index: Int): InputUnit {
                return when (index) {
                    SG.dropdownIndex -> SG
                    BRIX.dropdownIndex -> BRIX
                    BAUME.dropdownIndex -> BAUME
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }

            fun getUnitLabelString(index: Int, context: Context): String {
                val inputUnitStrings = context.resources.getStringArray(R.array.input_units_labels)
                if (index >= inputUnitStrings.size) {
                    throw IllegalArgumentException("Unknown index: $index")
                }
                return inputUnitStrings[index]
            }
        }
    }

    /**
     * An enum to represent the different output units available.
     */
    enum class OutputUnit(private val radioIndex: Int) {
        ABV(0), ABW(1);

        companion object {
            fun fromIndex(index: Int): OutputUnit {
                return when (index) {
                    ABV.radioIndex -> ABV
                    ABW.radioIndex -> ABW
                    else -> throw IllegalArgumentException("Unknown index: $index")
                }
            }

            fun getUnitLabelString(index: Int, context: Context): String {
                val outputUnitStrings = context.resources.getStringArray(R.array.output_units)
                if (index >= outputUnitStrings.size) {
                    throw IllegalArgumentException("Unknown index: $index")
                }
                return outputUnitStrings[index]
            }
        }
    }
}
