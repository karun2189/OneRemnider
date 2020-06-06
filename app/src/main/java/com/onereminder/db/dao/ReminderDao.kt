package com.onereminder.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.onereminder.db.entity.Reminder
import com.onereminder.db.helper.ReminderListHelper

@Dao
abstract class ReminderDao : BaseDao<Reminder> {

    @Transaction
    @Query("SELECT * FROM rme_reminder  WHERE type = :type")
    abstract fun getReminderByType(type: Int): List<ReminderListHelper>

    @Transaction
    @Query("SELECT * FROM rme_reminder WHERE r_id = :id")
    abstract fun getReminderById(id: Long): Reminder?

    @Transaction
    open fun insertOrUpdate(entity: Reminder): Long {
        var id: Long = insertIgnore(entity)
        if (id == -1L) {
            update(entity)
            id = entity.id
        }
        entity.id = id
        return id
    }

    @Transaction
    open fun insertOrUpdate(entities: List<Reminder>) {
        entities.forEach { insertOrUpdate(it) }
    }
}