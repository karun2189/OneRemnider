package com.onereminder.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "rme_reminder")
class Reminder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "r_id")
    var id: Long = 0

//    @ColumnInfo(name = "recurrence_time")
//    var recurrenceTime: String? = null

    @ColumnInfo(name = "reminder_time")
    var reminderTime: Date? = null

//    @ColumnInfo(name = "is_viewed")
//    var isViewed: Boolean? = false

    @ColumnInfo(name = "type")
    var type: Int = 0

//    @ColumnInfo(name = "latitude")
//    var latitude: Long = 0

//    @ColumnInfo(name = "longitude")
//    var longitude: Long = 0

//    @ColumnInfo(name = "is_expired")
//    var isExpired: Boolean? = null

    @ColumnInfo(name = "type_id")
    var typeId: Long = 0

//    @ColumnInfo(name = "is_intimate_before")
//    var isIntimateBefore: Boolean? = false

    @ColumnInfo(name = "title")
    var title: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

//    @ColumnInfo(name = "intimate_before_time")
//    var intimateBeforeTime: Date? = null

}