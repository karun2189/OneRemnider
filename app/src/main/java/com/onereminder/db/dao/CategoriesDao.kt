package com.onereminder.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.onereminder.db.entity.Categories

@Dao
abstract class CategoriesDao : BaseDao<Categories> {

    @Transaction
    @Query("SELECT * FROM rme_categories")
    abstract fun getAllCategories(): MutableList<Categories>

    @Transaction
    open fun insertOrUpdate(entity: Categories): Long {
        var id: Long = insertIgnore(entity)
        if (id == -1L) {
            update(entity)
            id = entity.id
        }
        entity.id = id
        return id
    }

    @Transaction
    open fun insertOrUpdate(entities: List<Categories>) {
        entities.forEach { insertOrUpdate(it) }
    }
}