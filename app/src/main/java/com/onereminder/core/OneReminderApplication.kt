package com.onereminder.core

import android.app.Application
import android.content.Context
import com.onereminder.db.core.DbHelper
import com.onereminder.db.core.OneReminderDB

class OneReminderApplication : Application() {

    companion object{
        var mContext : Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        // Init values
        mContext = this
        DbHelper(this).insertDefaultCategories()
    }
}