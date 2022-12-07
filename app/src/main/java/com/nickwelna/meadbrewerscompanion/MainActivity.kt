package com.nickwelna.meadbrewerscompanion

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcoholCalculatorUtil.InputUnit
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcoholCalculatorUtil.OutputUnit
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcoholCalculatorUtil.calcPotentialAlcohol
import com.nickwelna.meadbrewerscompanion.ui.theme.MeadBrewersCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeadBrewersCompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {}
                ABVCalc()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementInput(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    isError: Boolean = false,
    supportingText: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged(onFocusChange),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = supportingText)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABVCalc() {
    var originalGravity by remember { mutableStateOf("0.000") }
    var finalGravity by remember { mutableStateOf("0.000") }
    var potentialAlcohol by remember { mutableStateOf("") }
    var inputUnits by remember { mutableStateOf(InputUnit.SG) }
    var outputUnits by remember { mutableStateOf(OutputUnit.ABV) }
    var inputUnitLabel by remember { mutableStateOf("Gravity") }
    var outputUnitLabel by remember { mutableStateOf("ABV") }
    var defaultValue by remember { mutableStateOf("0.000") }
    var finalMeasurementError by remember { mutableStateOf(false) }
    val options = listOf("Specific Gravity", "BRIX", "Baume")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }


    Column(modifier = Modifier.padding(top = 16.dp)) {
        // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Input Unit") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            val inputUnitIndex = options.indexOf(selectionOption)
                            selectedOptionText = selectionOption
                            inputUnits = InputUnit.fromIndex(inputUnitIndex)
                            inputUnitLabel = InputUnit.getUnitLabelString(inputUnitIndex)
                            defaultValue =
                                if (inputUnits == InputUnit.BAUME || inputUnits == InputUnit.BRIX) {
                                    "00.00"
                                } else {
                                    "0.000"
                                }
                            originalGravity = defaultValue
                            finalGravity = defaultValue
                            potentialAlcohol = ""
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MeasurementInput(
            value = originalGravity,
            "Original $inputUnitLabel",
            onValueChange = {
                if (it.length <= 5) {
                    originalGravity = it
                }
            },
            onFocusChange = {
                originalGravity =
                    if (!it.hasFocus && originalGravity.isEmpty()) "0.000" else originalGravity
            })

        Spacer(modifier = Modifier.height(16.dp))

        MeasurementInput(
            value = finalGravity,
            "Final $inputUnitLabel",
            onValueChange = {
                if (it.length <= 5) {
                    finalGravity = it
                }
            },
            onFocusChange = {
                finalGravity =
                    if (!it.hasFocus && finalGravity.isEmpty()) "0.000" else finalGravity
                finalMeasurementError = errorCheckInputs(originalGravity, finalGravity)
            },
            isError = finalMeasurementError,
            supportingText = "Final $inputUnitLabel must be less than Original $inputUnitLabel"
        )
        Spacer(modifier = Modifier.height(16.dp))
        val radioOptions = listOf("ABV", "ABW")
        var selectedOption by remember { mutableStateOf(radioOptions[0]) }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Row(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = text == selectedOption,
                                onClick = {
                                    selectedOption = text
                                    val outputUnitIndex = radioOptions.indexOf(selectedOption)
                                    outputUnits = OutputUnit.fromIndex(outputUnitIndex)
                                    outputUnitLabel = OutputUnit.getUnitLabelString(outputUnitIndex)
                                    if (originalGravity.isEmpty()) {
                                        originalGravity = defaultValue
                                    }
                                    if (finalGravity.isEmpty()) {
                                        finalGravity = defaultValue
                                    }
                                    finalMeasurementError =
                                        errorCheckInputs(originalGravity, finalGravity)
                                    if (potentialAlcohol != "" && !finalMeasurementError) {
                                        potentialAlcohol = "%.4g".format(
                                            calcPotentialAlcohol(
                                                originalGravity.toFloat(),
                                                finalGravity.toFloat(),
                                                inputUnits,
                                                outputUnits
                                            )
                                        )
                                    }
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = text == selectedOption,
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    finalMeasurementError = errorCheckInputs(originalGravity, finalGravity)
                    if (!finalMeasurementError) {
                        // Maintain proper number of significant figures
                        potentialAlcohol = "%.4g".format(
                            calcPotentialAlcohol(
                                originalGravity.toFloat(),
                                finalGravity.toFloat(),
                                inputUnits,
                                outputUnits
                            )
                        )
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
            ) {
                Text("Calculate $outputUnitLabel")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Calculated $outputUnitLabel is $potentialAlcohol%",
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

private fun errorCheckInputs(
    originalGravity: String,
    finalGravity: String,
): Boolean {
    val originalFloat = originalGravity.toFloat()
    val finalFloat = finalGravity.toFloat()
    return originalFloat < finalFloat
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun GreetingPreview() {
    MeadBrewersCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {}
        ABVCalc()
    }
}