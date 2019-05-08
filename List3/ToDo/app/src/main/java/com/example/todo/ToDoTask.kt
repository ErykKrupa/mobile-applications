package com.example.todo

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@SuppressLint("ParcelCreator")
class ToDoTask(var name: String, var deadline: Date, var priority: Priority, var type: ToDoType) : Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented")
    }

    override fun describeContents(): Int {
        TODO("not implemented")
    }
}