package com.example.simpletodo

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_new_task.*
import java.util.*

class NewTaskDialogFragment : DialogFragment() {

    private var category = 0

    override fun onStart() {
        super.onStart()
        cancelButton.setOnClickListener { dialog.dismiss() }
        okButton.setOnClickListener {
            if (editText.text.isNotEmpty()) {
                val task = Task(
                    editText.text.toString(),
                    Date(calendarView.date), category
                )
                    AsyncTask.execute { TaskDatabase.getInstance(context!!).TaskDao().insert(task) }
                    dialog.dismiss()
            } else view?.let {
                Snackbar.make(it, "Text must not be empty", Snackbar.LENGTH_SHORT).show()
            }
        }
        imageButton.setOnClickListener {
            category++
            val colors = resources.obtainTypedArray(R.array.category_colors)
            val drawables = resources.obtainTypedArray(R.array.category_graphics)

            if (!colors.hasValue(category) || !drawables.hasValue(category))
                category = 0

            val color = colors.getColor(category, -1)
            val drawable = drawables.getResourceId(category, -1)
            imageButton.background.setTint(color)
            imageButton.setImageResource(drawable)
            colors.recycle()
            drawables.recycle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_new_task, container, false)
    }
}
