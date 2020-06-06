package com.onereminder.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.onereminder.reminder.ReminderUtils.KEY_REMINDER_ID
import java.util.*

abstract class BaseReminder constructor(private val mContext: Context?) {

    /** Set reminder start's here**/
    open fun setReminder(
        alarmId: String,
        reminderId: Long,
        calendar: Calendar
    ) {
        setReminderBasedOnAlarmManager(alarmId, calendar, reminderId)
    }

    private fun setReminderBasedOnAlarmManager(
        alarmId: String,
        calendar: Calendar,
        reminderId: Long
    ) {
        mContext?.let {
            val alarmMgr = it.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val toIntent = Intent(it, ReminderAlarmReceiver::class.java).apply {
                putExtra(KEY_REMINDER_ID, reminderId)
            }
            val pendingIntent = PendingIntent.getBroadcast(
                it,
                Integer.parseInt(alarmId),
                toIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> alarmMgr.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
                )
                else -> alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }
    }
    /** Set reminder end's here**/

    /** Remove reminder start's here**/
    open fun cancelScheduledReminder(reminderId: String) {
        cancelAlarmManager(reminderId)
    }

    private fun cancelAlarmManager(alarmId: String) {
        mContext?.let {
            val alarmIntent = PendingIntent.getBroadcast(
                mContext,
                Integer.parseInt(alarmId),
                Intent(mContext, ReminderAlarmReceiver::class.java),
                0
            )
            (it.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(alarmIntent)
        }
    }
}