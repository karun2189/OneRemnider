package com.onereminder.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.onereminder.db.core.ifLet
import com.onereminder.reminder.ReminderUtils.KEY_REMINDER_ID

class ReminderAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        ifLet(context, intent) {
            val service = Intent(context, ReminderAlarmService::class.java)
            service.putExtra(KEY_REMINDER_ID, intent!!.getLongExtra(KEY_REMINDER_ID, 0))
            context!!.startService(service)
        }
    }
}