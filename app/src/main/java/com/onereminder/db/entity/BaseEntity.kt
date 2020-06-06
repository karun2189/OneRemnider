package com.onereminder.db.entity

import androidx.room.Ignore

open class BaseEntity {

    @Ignore
    var mReminder: Reminder? = null
}