package com.onereminder.db.dao

import androidx.room.*

interface BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Transaction
    @Insert
    fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param obj the objects to be inserted.
     */
    @Transaction
    @Insert
    fun insert(vararg obj: T)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Transaction
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Transaction
    @Delete
    fun delete(obj: T)

    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIgnore(obj: T): Long

    @Transaction
    @Insert
    fun insertAll(users: List<T>)
}
