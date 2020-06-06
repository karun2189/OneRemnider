package com.onereminder.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rme_call")
class Call : BaseEntity() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "r_id")
    var id: Long = 0

    @ColumnInfo(name = "number")
    var phoneNumber: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null
}