package com.onereminder.db.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onereminder.db.dao.CallDao
import com.onereminder.db.dao.CategoriesDao
import com.onereminder.db.dao.ReminderDao
import com.onereminder.db.entity.Call
import com.onereminder.db.entity.Categories
import com.onereminder.db.entity.Reminder

@Database(entities = [Call::class, Categories::class, Reminder::class], version = 1)
@TypeConverters(DateConverter::class, IntConverter::class)
abstract class OneReminderDB : RoomDatabase() {

    abstract val callDao: CallDao
    abstract val reminderDao: ReminderDao
    abstract val categoriesDao: CategoriesDao

    companion object {

        private const val ONE_DB = "oneDatabase"

        @Volatile
        private var instance: OneReminderDB? = null

        /** Using Named Parameter **/
        fun getDatabase(context: Context, dbName: String = ONE_DB): OneReminderDB? {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context = context, dbName = dbName).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, dbName: String): OneReminderDB {
            return Room.databaseBuilder(
                context.applicationContext,
                OneReminderDB::class.java,
                dbName
            ).build()
        }

        private fun destroyInstance() {
            instance = null
        }

        fun deleteUserDB(context: Context?, dbName: String = ONE_DB): Boolean {
            if (!doesDatabaseExist(
                    context = context, dbName = dbName
                )
            ) {
                return true
            }
            val value = context?.deleteDatabase(dbName)
            destroyInstance()
            return value ?: false
        }

        private fun doesDatabaseExist(context: Context?, dbName: String = ONE_DB): Boolean {
            return context?.getDatabasePath(dbName)?.exists() ?: false
        }
    }
}