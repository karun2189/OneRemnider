package com.onereminder.db.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.onereminder.db.entity.Call

@Dao
abstract class CallDao : BaseDao<Call> {

    @Transaction
    open fun insertOrUpdate(entity: Call): Long {
        var id: Long = insertIgnore(entity)
        if (id == -1L) {
            update(entity)
            id = entity.id
        }
        entity.id = id
        return id
    }

    @Transaction
    open fun insertOrUpdate(entities: List<Call>) {
        entities.forEach { insertOrUpdate(it) }
    }
}