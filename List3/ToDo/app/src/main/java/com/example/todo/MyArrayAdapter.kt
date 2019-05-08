package com.example.todo

import android.annotation.SuppressLint
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.time.ZoneId

@SuppressLint("ParcelCreator")
class MyArrayAdapter(context: Context, private var data: ArrayList<ToDoTask>) :
    ArrayAdapter<ToDoTask>(context, R.layout.my_item, data), Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented")
    }

    override fun describeContents(): Int {
        TODO("not implemented")
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.my_item, parent, false)
        }
        val localDate = data[position].deadline.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val year = localDate.year.toString()
        val month = (if (localDate.monthValue > 9) "" else "0") + localDate.monthValue.toString()
        val day = (if (localDate.dayOfMonth > 9) "" else "0") + localDate.dayOfMonth.toString()
        view!!.findViewById<TextView>(R.id.name).text = data[position].name
        view.findViewById<TextView>(R.id.deadline).text = "$day.$month.$year"
        view.findViewById<TextView>(R.id.priority).text = data[position].priority.name
        view.findViewById<ImageView>(R.id.image).setImageResource(MainActivity.getImage(data[position].type))
        return view
    }
}