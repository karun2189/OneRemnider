package com.onereminder.ui

import android.os.Bundle
import com.onereminder.R
import com.onereminder.core.BaseActivity
import com.onereminder.ui.categories.CategoriesFragment

class CategoriesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.root_container, CategoriesFragment())
//            .commitAllowingStateLoss()
    }
}