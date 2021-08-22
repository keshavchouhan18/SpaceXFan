package com.spacexfanapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.spacexfanapplication.SpaceXApplication
import com.spacexfanapplication.SpaceXApplication.Companion.context
import org.koin.core.KoinComponent

class AppUtils : KoinComponent {

    companion object {

        private const val TAG = "AppUtils"

        fun isEmpty(`object`: Any?): Boolean {
            if (`object` == null) return true
            return if (`object` is Collection<*>) {
                `object`.size == 0
            } else if (`object` is String) {
                isStringEmpty(`object` as String?)
            } else {
                throw IllegalArgumentException("You can only pass String and Collection type objects in this method")
            }
        }

        fun isNetworkConnected(): Boolean {
            val cm = SpaceXApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected &&
                    netInfo.isConnectedOrConnecting
        }

        fun isStringEmpty(value: String?): Boolean {
            return value == null || value.trim { it <= ' ' }.isEmpty()

        }

        fun showKeyboard(context: Context) {
            val imm: InputMethodManager? = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun isNotEmpty(str: String?): Boolean {
            return str != null && str.isNotEmpty()
        }
    }
}

fun getString(@StringRes id: Int): String {
    return context.resources.getString(id)
}