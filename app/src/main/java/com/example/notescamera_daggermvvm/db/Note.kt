package com.example.notescamera_daggermvvm.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note_Table")
data class Note(@PrimaryKey(autoGenerate = true)
                var idNumber: Int?,
                @ColumnInfo(name = "Title")
                var title:String?,
                @ColumnInfo(name = "Description")
                var descr:String?,
                @ColumnInfo(name = "Priority")
                var prio:Int,
                @ColumnInfo(name = "Picture")
                var pic:ByteArray?=null)