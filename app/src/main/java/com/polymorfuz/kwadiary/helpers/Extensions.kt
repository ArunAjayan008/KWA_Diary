package com.polymorfuz.kwadiary.helpers


import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.polymorfuz.kwadiary.R
import com.google.android.material.snackbar.Snackbar

fun Context.setSnackbar(message: String, view: View) {
    val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
    val snackbarView = snackbar.view
    snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
    snackbar.show()
}

fun Context.showShortToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.showLongToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.showErrorToast() =
    Toast.makeText(this, this.getString(R.string.error_common), Toast.LENGTH_LONG).show()

fun String.isEmail(): Boolean {
    return try {
        Patterns.EMAIL_ADDRESS.matcher(this).matches()
    } catch (e: Exception) {
        false
    }
}


fun String.isPhoneNumber(): Boolean {
    return try {
        Patterns.PHONE.matcher(this).matches()
    } catch (e: Exception) {
        false
    }
}

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }


fun <V> HashMap<String, V>.toBundle(bundle: Bundle = Bundle()): Bundle = bundle.apply {
    forEach {
        val k = it.key
        val v = it.value
        when (v) {
            is Bundle -> putBundle(k, v)
            is Int -> putInt(k, v)
            is Byte -> putByte(k, v)
            is ByteArray -> putByteArray(k, v)
            is Char -> putChar(k, v)
            is CharArray -> putCharArray(k, v)
            is CharSequence -> putCharSequence(k, v)
            is Float -> putFloat(k, v)
            is FloatArray -> putFloatArray(k, v)
            is Short -> putShort(k, v)
            is ShortArray -> putShortArray(k, v)
            is String -> putString(k, v)
            is Boolean -> putBoolean(k, v)
            is Long -> putLong(k, v)

            else -> throw IllegalArgumentException("$v is of a type that is not currently supported")
        }
    }
}