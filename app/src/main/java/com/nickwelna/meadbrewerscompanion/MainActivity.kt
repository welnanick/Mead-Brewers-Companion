package com.nickwelna.meadbrewerscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.nickwelna.meadbrewerscompanion.ui.screens.HelpDialog
import com.nickwelna.meadbrewerscompanion.ui.screens.PotentialAlcoholCalc
import com.nickwelna.meadbrewerscompanion.ui.theme.MeadBrewersCompanionTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeadBrewersCompanionTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            "Potential Alcohol Calculator",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }, actions = {
                        val openDialog = remember { mutableStateOf(false) }
                        IconButton(onClick = { openDialog.value = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_help_outline_24),
                                contentDescription = stringResource(id = R.string.help_content_description)
                            )
                        }
                        HelpDialog(openDialog = openDialog)
                    })
                }, content = { innerPadding ->
                    PotentialAlcoholCalc(innerPadding)
                })
            }
        }
    }
}