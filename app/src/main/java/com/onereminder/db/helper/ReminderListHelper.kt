package com.onereminder.db.helper

import androidx.room.Embedded
import androidx.room.Relation
import com.onereminder.db.entity.Call
import com.onereminder.db.entity.Reminder

class ReminderListHelper {

    @Embedded
    var mReminder: Reminder? = null

    @Relation(parentColumn = "type_id", entityColumn = "r_id", entity = Call::class)
    var mCallList: List<Call>? = null
}