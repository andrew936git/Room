package com.example.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
class Contact(
    @ColumnInfo(name = "surname") var surname: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String,
    @ColumnInfo(name = "date") var date: String) {

    @PrimaryKey(autoGenerate = true) var id = 0
}