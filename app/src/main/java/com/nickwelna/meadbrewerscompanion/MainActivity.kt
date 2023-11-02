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
import logcat.LogPriority
import logcat.logcat

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logcat(LogPriority.INFO) { "#onCreate" }
        setContent {
            MeadBrewersCompanionTheme {
                Scaffold(topBar = {
                    CenterAlignedTopAppBar(title = {
                        Text(
                            stringResource(R.string.potential_alcohol_screen_title),
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