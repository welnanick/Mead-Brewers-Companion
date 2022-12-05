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