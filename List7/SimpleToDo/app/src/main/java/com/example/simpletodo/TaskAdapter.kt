package com.example.simpletodo

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filterable
import kotlinx.android.synthetic.main.list_element.view.*
import java.text.SimpleDateFormat

class TaskAdapter(context: Context?, objects: MutableList<Task>?) :
    ArrayAdapter<Task>(context, R.layout.list_element, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_element, parent, false)
        val task = getItem(position)

        val colors = context.resources.obtainTypedArray(R.array.category_colors)
        val drawables = context.resources.obtainTypedArray(R.array.category_graphics)

        if (colors.hasValue(task.category) && drawables.hasValue(task.category)) {

//            val color = colors.getColor(task.category, -1)
            val color = colors.getColor(0, -1)
            val drawable = drawables.getResourceId(task.category, -1)

            view.imageView.background.setTint(color)
            view.imageView.setImageResource(drawable)

        }
        view.contentTextView.text = task.text
//        view.dateTextView.text = task.date.toLocaleString()
        view.dateTextView.text = SimpleDateFormat("yyyy-MM-dd").format(task.date)
        if (task.done) view.checkBox.isChecked = true
        view.checkBox.setOnClickListener { AsyncTask.execute { TaskDatabase.getInstance(context).TaskDao().setDone(task.id!!,view.checkBox.isChecked ) } }
        colors.recycle()
        drawables.recycle()
        return view
    }
}