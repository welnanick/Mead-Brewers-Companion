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

import android.app.Application
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class MeadBrewersCompanionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Log all priorities in debug builds, no-op in release builds.
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
    }
}