package com.gskose.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Typeface
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.gskose.repository.AppConstants
import com.gskose.activity.base.BaseActivity
import com.gskose.BuildConfig
import com.gskose.R
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

object CommonUtils {
    private var TYPE_WIFI = 1
    private var TYPE_MOBILE = 2
    private var TYPE_NOT_CONNECTED = 0

    fun getConnectivityStatus(context: Context): Int {
        val activeNetwork = (context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager).activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): String {
        val conn = getConnectivityStatus(context)
        var status: String? = null
        when (conn) {
            TYPE_WIFI -> {
                status = AppConstants.CONNECT_TO_WIFI
            }
            TYPE_MOBILE -> {
                status = getNetworkClass(context)
            }
            TYPE_NOT_CONNECTED -> {
                status = AppConstants.NOT_CONNECT
            }
        }
        return status!!
    }

    private fun getNetworkClass(context: Context): String {
        val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager).activeNetworkInfo
        if (info == null || !info.isConnected)
            return "-" //not connected
        if (info.type == ConnectivityManager.TYPE_WIFI)
            return "WIFI"
        if (info.type == ConnectivityManager.TYPE_MOBILE) {
            return when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_HSPAP  //api<13 : replace by 15
                -> "3G"
                TelephonyManager.NETWORK_TYPE_LTE    //api<11 : replace by 13
                -> "4G"
                else -> "UNKNOWN"
            }
        }
        return "UNKNOWN"
    }

    fun checkRotationFromCamera(bitmap: Bitmap, rotate: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private const val EMAIL_PATTERN: String =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun emailValidation(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String? {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getDeviceName(): String {
        return Build.MODEL + " " + Build.VERSION.RELEASE
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()_<>~`{}:])(?=\\S+$).{8,30}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password!!)
        return matcher.matches()
    }

    fun getDeviceOS(): String {
        return Build.VERSION.SDK_INT.toString()
    }

    fun getAppVersion(): String {
        return BuildConfig.VERSION_NAME
    }

    fun getDeviceType(): String {
        return "android"
    }

    fun isEmailValid(email: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches()
    }

    fun fireIntent(context: BaseActivity, uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    fun fireIntent(
        baseActivity: BaseActivity,
        intent: Intent,
        requestCode: Int,
        fragment: Fragment?,
        isNewActivity: Boolean,
        isFinished: Boolean
    ) {
        if (isFinished)
            baseActivity.finish()
        baseActivity.startActivityForResult(intent, requestCode)
        if (!isNewActivity) {
            baseActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun fireIntent(
        baseActivity: BaseActivity,
        intent: Intent,
        isNewActivity: Boolean,
        isFinished: Boolean
    ) {
        if (isFinished)
            baseActivity.finish()
        baseActivity.startActivity(intent)
        if (!isNewActivity) {
            baseActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun fireIntent(
        baseActivity: BaseActivity,
        intent: Intent,
        isNewActivity: Boolean,
        isFinished: Boolean,
        bundle: Bundle
    ) {
        if (isFinished)
            baseActivity.finish()
        baseActivity.startActivity(intent, bundle)
        if (!isNewActivity) {
            baseActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun fireIntent(
        baseActivity: BaseActivity,
        cls: Class<*>,
        isNewActivity: Boolean,
        isFinished: Boolean
    ) {
        if (isFinished)
            baseActivity.finish()
        val i = Intent(baseActivity, cls)
        baseActivity.startActivity(i)
        if (!isNewActivity) {
            baseActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    open fun fireIntent(baseActivity: BaseActivity, isNewActivity: Boolean) {
        baseActivity.finish()
        if (!isNewActivity) {
            baseActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            baseActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun fireIntentWithClearFlag(context: BaseActivity, intent: Intent, isNewActivity: Boolean) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        if (!isNewActivity) {
            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun rotationImage(filePath: String): Bitmap {
        val exif = ExifInterface(filePath)

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        var angle = 0
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                angle = 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                angle = 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                angle = 270
            }
        }

        val mat = Matrix()
        mat.postRotate(angle.toFloat())
        val bmp = BitmapFactory.decodeStream(FileInputStream(filePath), null, null)
        return Bitmap.createBitmap(bmp!!, 0, 0, bmp.width, bmp.height, mat, true)
    }

    fun passwordValidation(password: String?): Boolean {
        val pattern: Pattern
        val passwordPattern =
            "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(passwordPattern)
        val matcher: Matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }

    fun getRegularFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/Nunito-Regular_0.ttf")
    }

    fun getBoldFont(_context: Context): Typeface {
        return Typeface.createFromAsset(_context.assets, "fonts/Nunito-SemiBold_0.ttf")
    }

    fun fireIntentWithURL(context: BaseActivity, uri: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

    fun hideStatusBar(activity: BaseActivity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun hideKeyPad(context: Context, view: View) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun checkImageRotationFromCamera(bitmap: Bitmap, rotate: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(rotate.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun getImageOrientation(imagePath: String?): Int {
        var rotate = 0
        try {
            val exif = imagePath?.let { ExifInterface(it) }
            when (exif!!.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return rotate
    }

    fun bitMapToStringImage(bitmap: Bitmap): String {
        val bas = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bas)
        val b = bas.toByteArray()
        return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
    }

    interface DialogOptionsSelectedListener {
        fun onSelect(isYes: Boolean)
    }

    fun bitMapToString(bitmap: Bitmap): String {
        val bas = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bas)
        val b = bas.toByteArray()
        return android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
    }

    fun showToast(activity: BaseActivity, message: String, type: Int) {
        val mdToast = MDToast.makeText(activity, message, MDToast.LENGTH_SHORT, type)
        mdToast.show()
    }

    fun showAlertDialogWithTwoOption(
        mContext: Context,
        positiveText: String,
        negativeText: String,
        message: String,
        dialogOptionsSelectedListener: DialogOptionsSelectedListener?
    ) {
        val builder = AlertDialog.Builder(mContext)
        builder.setMessage(message)
            .setCancelable(true)
        if (positiveText.trim { it <= ' ' }.isNotEmpty()) {
            builder.setPositiveButton(positiveText) { dialog, _ ->
                dialogOptionsSelectedListener?.onSelect(true)
                dialog.dismiss()
            }
        }
        if (negativeText.trim { it <= ' ' }.isNotEmpty()) {
            builder.setNegativeButton(negativeText) { dialog, _ ->
                dialogOptionsSelectedListener?.onSelect(false)
                dialog.dismiss()
            }
        }
        val alert = builder.create()
        alert.setCanceledOnTouchOutside(false)
        alert.setCancelable(false)
        alert.show()
    }

    fun checkEditTextNullOrNot(editText: TfEditText): Boolean {
        var result = false
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isEmpty() && p0.isNullOrEmpty() && p0 == "") {
                    result = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        return result
    }

    @SuppressLint("SimpleDateFormat")
    fun getDate(originalFormat: String, targetFormat: String, date: String?): String {
        return if (date != null) {
            try {
                val of = SimpleDateFormat(originalFormat).parse(date)
                val tf = SimpleDateFormat(targetFormat)
                tf.format(of!!)
            } catch (e: Exception) {
                date
            }
        } else {
            ""
        }
    }

}