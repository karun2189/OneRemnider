package com.onereminder.db.entity

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.onereminder.R

@Entity(tableName = "rme_categories")
class Categories {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "r_id")
    var id: Long = 0

    @ColumnInfo(name = "type")
    var type: Int = 0

//    @ColumnInfo(name = "total")
//    var total: Int? = 0

//    @ColumnInfo(name = "finished")
//    var finished: Int? = 0

//    @ColumnInfo(name = "order")
//    var order: Int? = 0

    @ColumnInfo(name = "type_name")
    var name: String? = null

    @ColumnInfo(name = "color")
    var color: Int = Color.BLUE

    @ColumnInfo(name = "resource")
    var icon: Int = R.drawable.ic_launcher_background
}