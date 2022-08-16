package com.polymorfuz.kwadiary.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun checkInternetAvailability(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (cm != null) {
                if (Build.VERSION.SDK_INT < 23) {
                    val ni = cm.activeNetworkInfo
                    if (ni != null) {
                        return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI
                                || ni.type == ConnectivityManager.TYPE_MOBILE)
                    }
                } else {
                    val n = cm.activeNetwork
                    return if (n != null) {
                        val nc = cm.getNetworkCapabilities(n)
                        (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
                    } else {
                        false
                    }
                }
            }
            true
        } catch (e: java.lang.Exception) {
            true
        }
    }

    fun getDisplayDimensions(activity: Activity): Point? {
        val point = Point()
        activity.windowManager.defaultDisplay.getSize(point)
        return point
    }

    fun getDisplayDimensions(activity: FragmentActivity): Point? {
        val point = Point()
        activity.windowManager.defaultDisplay.getSize(point)
        return point
    }

    fun convertDpToPx(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density
    }

    fun convertPixelsToDp(context: Context, px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDate(strDate: String, inputFormat: String, outPutFormat: String): String {
        val spfInput = SimpleDateFormat(inputFormat)
        val spfOutput = SimpleDateFormat(outPutFormat)
        var date: Date? = null
        try {
            date = spfInput.parse(strDate)
            return spfOutput.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return "1994-10-09"
        }
    }

    fun isFutureDate(strDate: String?, inputFormat: String): Boolean {
        if (strDate == null)
            return false

        val spfInput = SimpleDateFormat(inputFormat)
        var date: Date? = null
        try {
            date = spfInput.parse(strDate)
            return date.after(Date(System.currentTimeMillis()))
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
    }

    fun convertDateToFormat(date: Date, output: String): String {
        val spfOutput = SimpleDateFormat(output)
        try {
            return spfOutput.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return "1994-10-09"
        }
    }

    fun capitalizeWords(data: String): String {
        return data.split(" ").map { it.capitalize() }.joinToString(" ")
    }

    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }

    fun processBitmapForProfileUpload(bitmap: Bitmap): Bitmap? {
        val height = bitmap.height
        val width = bitmap.width
        return try {
            if (height <= 512 && width <= 512) {
                bitmap
            } else {
                if (height > width) {
                    val ratio = height.toFloat() / width
                    val proccessedWidth = 512.toFloat() / ratio
                    return Bitmap.createScaledBitmap(bitmap, proccessedWidth.toInt(), 512, true)
                }
                if (width > height) {
                    val ratio = width.toFloat() / height
                    val processedHeight = 512.toFloat() / ratio
                    return Bitmap.createScaledBitmap(bitmap, 512, processedHeight.toInt(), true)
                }
                if (height == width) {
                    Bitmap.createScaledBitmap(bitmap, 512, 512, true)
                } else {
                    bitmap
                }
            }
        } catch (e: Exception) {
            bitmap
        }
    }

    fun bitmapToFile(bitmap: Bitmap, context: Context): File? {
        val filesDir = context.filesDir
        val name: String = Date().time.toString()
        val imageFile = File(filesDir, "$name.jpg")
        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: java.lang.Exception) {
        }
        return imageFile
    }

}