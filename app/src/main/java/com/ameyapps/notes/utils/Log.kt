package com.ameyapps.notes.utils

import android.util.Log

open class Log {

    companion object {

        var showLogs: Boolean = true

        fun d(tag: String, message: String) {
            if (showLogs) {
                Log.d(tag, message)
            }
        }

        fun e(tag: String, message: String) {
            if (showLogs) {
                Log.e(tag, message)
            }
        }

        fun w(tag: String, message: String) {
            if (showLogs) {
                Log.w(tag, message)
            }
        }
    }
}