package com.onereminder.db.core

import android.content.Context
import com.onereminder.R
import com.onereminder.core.OneReminderApplication
import com.onereminder.db.core.Utils.R_CALL
import com.onereminder.db.entity.Call
import com.onereminder.db.entity.Categories
import com.onereminder.db.entity.Reminder
import com.onereminder.db.helper.ReminderListHelper
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.annotations.NotNull

class DbHelper(private val mContext: Context) {

    private var mDataBase: OneReminderDB = OneReminderDB.getDatabase(mContext)!!

    fun insertDefaultCategories() {
        doAsync {
            if (getAllCategories().isEmpty()) {
                var color = 0
                val colorList =
                    OneReminderApplication.mContext!!.resources.getIntArray(R.array.categories_bg_color)
                val categories = ArrayList<Categories>()
                Utils.CATEGORIES_LIST.forEach {
                    categories.add(Categories().apply {
                        this.type = it.key
                        this.name = it.value
                        this.color = colorList[color]
                        this.icon = when (it.key) {
                            R_CALL -> {
                                R.drawable.ic_call
                            }
                            /*R_EMAIL -> {
                                R.drawable.ic_email
                            }
                            R_EVENT -> {
                                R.drawable.ic_event
                            }
                            R_EMI -> {
                                R.drawable.ic_emi
                            }
                            R_MEDICINE -> {
                                R.drawable.ic_medicine
                            }
                            R_TASK -> {
                                R.drawable.ic_task
                            }*/
                            else -> {
                                R.drawable.ic_launcher_background
                            }
                        }
                        color++
                    })
                }
                if (categories.isNotEmpty()) {
                    mDataBase.categoriesDao.insertOrUpdate(categories)
                }
            }
        }
    }

    fun getAllCategories(): List<Categories> =
        doAsyncResult { mDataBase.categoriesDao.getAllCategories() }.get()


    fun insertOrUpdateReminder(@NotNull obj: Any): Reminder? {
        return doAsyncResult {
            when (obj) {
                is Call -> {
                    mDataBase.callDao.insertOrUpdate(obj).let { id ->
                        obj.mReminder?.let { reminder ->
                            reminder.type = R_CALL
                            reminder.typeId = id
                            mDataBase.reminderDao.insertOrUpdate(reminder)
                            reminder
                        }
                    }
                }

                else -> null
            }
        }.get()
    }

    fun getReminderById(id: Long): Reminder? =
        doAsyncResult { mDataBase.reminderDao.getReminderById(id) }.get()

    fun getReminderListByType(type: Int): List<ReminderListHelper> {
        return (doAsyncResult {
                mDataBase.reminderDao.getReminderByType(type = type)
            }.get() ?: emptyList())
    }
}