package com.nickwelna.meadbrewerscompanion.calculators

object ABVCalculatorUtil {
    fun calcABV(originalGravity: Float, finalGravity: Float): Float {
        return specificGravityToAlcoholByVolume(1 + originalGravity - finalGravity)
    }

    private fun specificGravityToAlcoholByVolume(specificGravity: Float): Float {
        return specificGravityToBaume(specificGravity)
    }

    private fun specificGravityToBaume(specificGravity: Float): Float {
        return 145 * (1 - (1 / specificGravity))
    }
}