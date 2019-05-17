package org.ameyapps.notes.utils

import android.content.Context
import android.widget.Toast
import java.text.DateFormat
import java.util.*

open class Utils {

    companion object {

        fun getDate(): String {
            var date: String = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)
                .format(Calendar.getInstance().time)
            return date
        }

        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

}