package com.onereminder.db.core

import android.content.Context
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.WindowManager
import com.onereminder.core.OneReminderApplication
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

object Utils {

    var R_CALL: Int = 1
//    var R_EMAIL: Int = 2
//    var R_EVENT: Int = 3
//    var R_DRINK_WATER: Int = 4
//    var R_EMI: Int = 5
//    var R_MEDICINE: Int = 6
//    var R_RECHARGE: Int = 7
//    var R_TRAVEL: Int = 8
//    var R_OTHERS: Int = 9
//    var R_TASK: Int = 10

    val CATEGORIES_LIST: HashMap<Int, String> = hashMapOf(R_CALL to "Call"/*, R_EMAIL to "Email", R_EVENT to "Event",
        /*R_DRINK_WATER to "Drink Water",*/ R_EMI to "Emi", R_MEDICINE to "Medicine", R_RECHARGE to "Recharge",
        R_TRAVEL to "Travel", R_OTHERS to "Others", R_TASK to "Task"*/)

    fun getCategoriesItemWidth(): Int {
        return getDisplayPixelWidth() / getColumnCount()
    }

    private fun getDisplayPixelWidth(): Int {
        return getDisplayPixelSize().x
    }

    private fun getDisplayPixelSize(): Point {
        val wm = OneReminderApplication.mContext
            ?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }

    fun getColumnCount(): Int {
        val mNumberOfColumns: Int = 2
        /*if (isTablet(context)) {
            val deviceWidth = getDisplayPixelWidth(context)
            val deviceHeight = getDisplayPixelHeight(context)
            if (isLandscape(context)) {
                // portrait column width
                val singleColumnWidth = deviceHeight / mDefaultPoraitColumnForTablet

                //Find number of columns in landscape
                mNumberOfColumns = Math.round(deviceWidth.toFloat() / singleColumnWidth)
            } else {
                mNumberOfColumns = mDefaultPoraitColumnForTablet
            }
        } else {
            mNumberOfColumns = mDefaultPoraitColumnForPhone
        }*/
        return mNumberOfColumns
    }

    fun isTablet(): Boolean {
        val context = OneReminderApplication.mContext
        val xlarge = context!!.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == 4
        val large = context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK == Configuration.SCREENLAYOUT_SIZE_LARGE
        return xlarge || large
    }

    fun setGradientColorToView(view: View?, color: Int?) {
        if (color != null && view != null && view.background is GradientDrawable) {
            val myGrad = view.background as GradientDrawable
            myGrad.setColor(color)
        }
    }

    fun getReminderDate(d: Date?): String {
        return SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(d)
    }

    fun getReminderTime(d: Date?): String {
        return SimpleDateFormat("h:mm a", Locale.ENGLISH).format(d)
    }
}