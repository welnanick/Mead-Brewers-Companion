package com.nickwelna.meadbrewerscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickwelna.meadbrewerscompanion.ui.theme.MeadBrewersCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeadBrewersCompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
                ABVCalc()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ABVCalc() {
    var originalGravity by remember { mutableStateOf("0.000") }
    var finalGravity by remember { mutableStateOf("0.000") }
    var abv by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 16.dp)) {


        TextField(
            value = originalGravity,
            onValueChange = {
                if (it.length <= 5) {
                    originalGravity = it
                }
            },
            singleLine = true,
            label = { Text("Original Gravity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .onFocusChanged {
                    originalGravity =
                        if (!it.hasFocus && originalGravity.isEmpty()) "0.000" else originalGravity
                }

        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = finalGravity,
            onValueChange = {
                if (it.length <= 5) {
                    finalGravity = it
                }
            },
            singleLine = true,
            label = { Text("Final Gravity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .onFocusChanged {
                    finalGravity =
                        if (!it.hasFocus && finalGravity.isEmpty()) "0.000" else finalGravity
                }

        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {abv = "%.2f".format(calcABV(originalGravity.toFloat(), finalGravity.toFloat()))},
            Modifier
                .align(Alignment.End)
                .padding(end = 16.dp)) {
            Text("Calculate ABV")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Calculated ABV is $abv", modifier = Modifier.padding(start = 16.dp), color = Color.White)
    }
}

fun calcABV(originalGravity: Float, finalGravity: Float): Float {

    return specificGravityToAlcoholByVolume(1+originalGravity-finalGravity)

}

fun specificGravityToAlcoholByVolume(specificGravity: Float): Float {
    return specificGravityToBaume(specificGravity)
}

fun specificGravityToBaume(specificGravity: Float): Float {
    return  145*(1-(1/specificGravity))
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MeadBrewersCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        }
        ABVCalc()
    }
}