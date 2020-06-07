package com.onereminder.reminder

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.onereminder.db.entity.Reminder
import java.util.*

class ReminderManager(private val mContext: Context?) : BaseReminder(mContext) {

    fun setReminder(reminder: Reminder?) {
        reminder?.let {
            it.reminderTime?.let { date ->
                if (isValidTime(date)) {
                    val fromCalendar = Calendar.getInstance()
                    fromCalendar.time = date

                    val reminderId = getReminderId(it.type, it.id)
                    if (isReminderActive(reminderId)) {
                        cancelScheduledReminder(reminderId)
                    }
                    setReminder(reminderId, it.id, fromCalendar)
                    Toast.makeText(mContext, "Reminder added successfully", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun cancelReminder(reminder: Reminder?) {
        reminder?.let {
            it.reminderTime?.let { _ ->
                val reminderId = getReminderId(it.type, it.id)
                if (isReminderActive(reminderId)) {
                    cancelScheduledReminder(reminderId)
                }
            }
        }
    }

    private fun isValidTime(date: Date): Boolean {
        return !(isTimeExceed(date))
    }

    private fun isTimeExceed(d1: Date): Boolean {
        return Date().after(d1)
    }

    private fun getReminderId(modelType: Int, modelId: Long): String {
        return StringBuilder().append(modelType).append(modelId).toString()
    }

    private fun isReminderActive(reminderId: String): Boolean {
        return PendingIntent.getBroadcast(
            mContext, Integer.parseInt(reminderId),
            Intent(mContext, ReminderAlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        ) != null
    }
}