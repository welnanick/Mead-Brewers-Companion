package com.nickwelna.meadbrewerscompanion.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.nickwelna.meadbrewerscompanion.R
import com.nickwelna.meadbrewerscompanion.calculators.PotentialAlcohol
import com.nickwelna.meadbrewerscompanion.ui.theme.MeadBrewersCompanionTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PotentialAlcoholCalc(paddingValues: PaddingValues) {
    val context = LocalContext.current
    var defaultValue by rememberSaveable {
        mutableStateOf(
            context.resources.getString(R.string.significant_gravity_default)
        )
    }
    var originalGravity by rememberSaveable { mutableStateOf(defaultValue) }
    var finalGravity by rememberSaveable { mutableStateOf(defaultValue) }
    var potentialAlcohol by rememberSaveable { mutableStateOf("") }
    var inputUnits by rememberSaveable { mutableStateOf(PotentialAlcohol.InputUnit.SG) }
    var outputUnits by rememberSaveable { mutableStateOf(PotentialAlcohol.OutputUnit.ABV) }
    var inputUnitLabel by rememberSaveable {
        mutableStateOf(
            context.resources.getString(R.string.input_unit_default)
        )
    }
    var outputUnitLabel by rememberSaveable {
        mutableStateOf(
            context.resources.getString(R.string.output_unit_default)
        )
    }
    var finalMeasurementError by rememberSaveable { mutableStateOf(false) }
    val options = stringArrayResource(R.array.input_units)
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf(options[0]) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.potential_alcohol_instruction_prompt),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        val items = stringArrayResource(id = R.array.potential_alcohol_instructions)
        val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 19.sp))
        Text(
            text = buildAnnotatedString {
                items.forEachIndexed { index, it ->
                    withStyle(style = paragraphStyle) {
                        append("${index + 1}. ")
                        append(it)
                    }
                }
            },
            modifier = Modifier.padding(start = 32.dp, end = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        ) // We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        ) {
            OutlinedTextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.input_unit_label)) },
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
                            inputUnits = PotentialAlcohol.InputUnit.fromIndex(inputUnitIndex)
                            inputUnitLabel =
                                PotentialAlcohol.InputUnit.getUnitLabelString(inputUnitIndex)
                            defaultValue =
                                if (inputUnits == PotentialAlcohol.InputUnit.BAUME || inputUnits == PotentialAlcohol.InputUnit.BRIX) {
                                    context.resources.getString(R.string.brix_baume_default)
                                } else {
                                    context.resources
                                        .getString(R.string.significant_gravity_default)
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
            stringResource(R.string.original_measure_label, inputUnitLabel),
            onValueChange = {
                if (it.length <= 5) {
                    originalGravity = it
                }
            },
            onFocusChange = {
                originalGravity =
                    if (!it.hasFocus && originalGravity.isEmpty()) context.resources.getString(R.string.significant_gravity_default) else if (it.hasFocus && (originalGravity == context.resources.getString(
                            R.string.significant_gravity_default
                        ) || originalGravity == context.resources.getString(R.string.brix_baume_default))
                    ) "" else originalGravity
            },
            contentDescription = context.resources
                .getString(R.string.original_measure_label, inputUnitLabel),
            imeAction = ImeAction.Next
        )

        Spacer(modifier = Modifier.height(16.dp))

        MeasurementInput(
            value = finalGravity,
            stringResource(R.string.final_measure_label, inputUnitLabel),
            onValueChange = {
                if (it.length <= 5) {
                    finalGravity = it
                }
            },
            onFocusChange = {
                finalGravity =
                    if (!it.hasFocus && finalGravity.isEmpty()) context.resources.getString(R.string.significant_gravity_default) else if (it.hasFocus && (finalGravity == context.resources.getString(
                            R.string.significant_gravity_default
                        ) || finalGravity == context.resources.getString(R.string.brix_baume_default))
                    ) "" else finalGravity
                if (!it.hasFocus) {
                    finalMeasurementError = errorCheckInputs(originalGravity, finalGravity)
                }
            },
            isError = finalMeasurementError,
            supportingText = context.resources
                .getString(R.string.final_measure_error_label, inputUnitLabel),
            contentDescription = context.resources
                .getString(R.string.final_measure_label, inputUnitLabel),
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(16.dp))
        val radioOptions = stringArrayResource(R.array.output_units)
        var selectedOption by rememberSaveable { mutableStateOf(radioOptions[0]) }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

            // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
            Row(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .height(56.dp)
                            .selectable(
                                selected = text == selectedOption, onClick = {
                                    selectedOption = text
                                    val outputUnitIndex = radioOptions.indexOf(selectedOption)
                                    outputUnits =
                                        PotentialAlcohol.OutputUnit.fromIndex(outputUnitIndex)
                                    outputUnitLabel =
                                        PotentialAlcohol.OutputUnit.getUnitLabelString(
                                            outputUnitIndex
                                        )
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
                                            PotentialAlcohol.calcPotentialAlcohol(
                                                originalGravity.toFloat(),
                                                finalGravity.toFloat(),
                                                inputUnits,
                                                outputUnits
                                            )
                                        )
                                    }
                                }, role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = text == selectedOption,
                            onClick = null // null recommended for accessibility with screen readers
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
                    if (originalGravity.isNotEmpty() && finalGravity.isNotEmpty()) {
                        finalMeasurementError = errorCheckInputs(originalGravity, finalGravity)
                        if (!finalMeasurementError) {

                            // Maintain proper number of significant figures
                            potentialAlcohol = "%.4g".format(
                                PotentialAlcohol.calcPotentialAlcohol(
                                    originalGravity.toFloat(),
                                    finalGravity.toFloat(),
                                    inputUnits,
                                    outputUnits
                                )
                            )
                            keyboardController?.hide()
                        }
                    }
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(context.getString(R.string.calculate_button_text, outputUnitLabel))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = context.getString(R.string.calculated_alcohol_title, outputUnitLabel),
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Text(
            text = "$potentialAlcohol%",
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge
        )
    }
}

@Composable
fun MeasurementInput(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    isError: Boolean = false,
    supportingText: String = "",
    contentDescription: String,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal, imeAction = imeAction
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged(onFocusChange)
            .semantics { this.contentDescription = contentDescription },
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = supportingText)
            }
        },
    )
}

@Composable
fun HelpDialog(openDialog: MutableState<Boolean>) {

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onDismissRequest = { // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
            openDialog.value = false
        }, text = {
            Column(Modifier.verticalScroll(state = rememberScrollState())) {
                Text(
                    text = stringResource(id = R.string.input_units_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.specific_gravity_label))
                        }
                        append(stringResource(id = R.string.specific_gravity_description))
                    }, style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.brix_label))
                        }
                        append(stringResource(id = R.string.brix_description))
                    }, style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.baume_label))
                        }
                        append(stringResource(id = R.string.baume_description))
                    }, style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.output_units_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.abv_label))
                        }
                        append(stringResource(id = R.string.abv_description))
                    }, style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.abw_label))
                        }
                        append(stringResource(id = R.string.abw_description))
                    }, style = MaterialTheme.typography.bodyLarge
                )
            }
        }, confirmButton = {/* Intentionally Blank */ }, dismissButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text(stringResource(id = R.string.close_button_text))
            }
        })
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun GreetingPreview() {
    MeadBrewersCompanionTheme { // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {}
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    "Potential Alcohol Calculator", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, actions = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_help_outline_24),
                        contentDescription = "Localized description"
                    )
                }
            })
        }, content = { paddingValues ->
            PotentialAlcoholCalc(paddingValues)
        })
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, name = "Dark Mode"
)
@Composable
fun AlertPreview() {
    MeadBrewersCompanionTheme {
        val openDialog = remember { mutableStateOf(true) }
        HelpDialog(openDialog)
    }
}