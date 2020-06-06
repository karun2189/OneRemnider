package com.onereminder.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.text.TextUtils
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.onereminder.R
import com.onereminder.db.core.DbHelper
import com.onereminder.db.entity.Reminder
import com.onereminder.reminder.ReminderUtils.KEY_REMINDER_ID
import org.jetbrains.annotations.NotNull

class ReminderAlarmService : IntentService("ReminderAlarmService") {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            handelReminder(it.getLongExtra(KEY_REMINDER_ID, 0))
        }
    }

    private fun handelReminder(id: Long) {
        DbHelper(mContext = baseContext).getReminderById(id)?.let {
            showSingleNotification(it, it.id.toInt())
        }
    }

    private fun showSingleNotification(@NotNull zReminder: Reminder, notificationId: Int) {

        val openIntent = Intent()
        val notificationActionIntent = Intent(/*baseContext,
        NoteBookBaseApplication.getNotificationActionService()*/
        )
        notificationActionIntent.putExtra(KEY_REMINDER_ID, zReminder.id)

        getNotificationBasedOnNote(zReminder, notificationActionIntent, notificationId).let {
            it.contentIntent = PendingIntent.getActivity(
                baseContext,
                notificationId,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            getNotificationManager().notify(notificationId, it)
        }
    }

    private fun getNotificationManager(): NotificationManager {
        return baseContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun getNotificationBasedOnNote(
        @NotNull zReminder: Reminder,
        notificationActionIntent: Intent?,
        notificationId: Int
    ): Notification {

        val reminderParams = ReminderNotificationParams().apply {
            title = zReminder.title
            content = zReminder.description
            defaultTitle = "Reminder"
            actionIntent = notificationActionIntent
            this.notificationId = notificationId
        }
        return generateTextNotificationForReminder(reminderParams)
    }

    private fun generateTextNotificationForReminder(reminderParams: ReminderNotificationParams): Notification {
        return getNotificationBuilder(
            reminderParams.title,
            reminderParams.content,
            reminderParams.defaultTitle
        ).apply {
            setAutoCancel(true)
            setSmallIcon(R.drawable.ic_launcher_background)
            /*if (reminderParams.actionIntent != null) {
                notificationCompatBuilder.addAction(
                    getCustomAction(
                        "DONE",
                        reminderParams.actionIntent!!,
                        reminderParams.notificationId!!
                    )
                )
                notificationCompatBuilder.addAction(
                    getCustomAction(
                        "Remind Me Tomorrow",
                        reminderParams.actionIntent!!,
                        reminderParams.notificationId!!
                    )
                )
            }*/

            if (reminderParams.bitmap != null) {
                setLargeIcon(reminderParams.bitmap)
            }
        }.build()
    }

    private fun getNotificationBuilder(
        title: String?,
        content: String?,
        defaultTitle: String
    ): NotificationCompat.Builder {

        val notificationChannelId = createNotificationChannelId()
        val notificationCompatBuilder =
            NotificationCompat.Builder(baseContext, notificationChannelId)

        notificationCompatBuilder
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .color = ContextCompat.getColor(baseContext, R.color.colorAccent)

        if (!TextUtils.isEmpty(title)) {

            notificationCompatBuilder.setContentTitle(title)

            if (!TextUtils.isEmpty(content)) {
                notificationCompatBuilder.setContentText(content)
            }
        } else if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {

            notificationCompatBuilder.setContentTitle(defaultTitle)

        } else if (TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {

            notificationCompatBuilder.setContentTitle(content)
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
            // visibility is set in the NotificationChannel.
            notificationCompatBuilder.setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notificationCompatBuilder.setCategory(Notification.CATEGORY_REMINDER)
            notificationCompatBuilder.priority = NotificationManager.IMPORTANCE_HIGH
        }

        return notificationCompatBuilder
    }

    private fun createNotificationChannelId(): String {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            val channelId = "NOTIFICATION_CHANNEL_ID"//no i18n

            // The user-visible name of the channel.
            val channelName = "Reminders"//no i18n

            // The user-visible description of the channel.
            val channelDescription = "This is a channel for reminder notifications"//no i18n

            // Initializes NotificationChannel.
            val notificationChannel = NotificationChannel(
                channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = channelDescription
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            getNotificationManager().createNotificationChannel(notificationChannel)
            return channelId
        }
        return ""//no i18n
    }

    private fun getCustomAction(
        actionType: String,
        actionIntent: Intent,
        notificationId: Int
    ): NotificationCompat.Action {
        actionIntent.action = actionType
        val snoozePendingIntent = PendingIntent.getService(
            baseContext,
            notificationId,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return NotificationCompat.Action.Builder(0, actionType, snoozePendingIntent).build()
    }

    class ReminderNotificationParams {
        var title: String? = null
        var content: String? = null

        @NotNull
        var defaultTitle: String = ""
        var actionIntent: Intent? = null
        var bitmap: Bitmap? = null
        var notificationId: Int? = null
    }
}