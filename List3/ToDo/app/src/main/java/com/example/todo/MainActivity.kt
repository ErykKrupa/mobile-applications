package com.example.todo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private var list = ArrayList<ToDoTask>()
    private var myAdapter: MyArrayAdapter? = null
    private var typesString = arrayOf("Work", "Study", "Home", "You", "Administration")
    private var ordersString = arrayOf("By Name", "By Priority", "By Type", "By Date")
    private var toDoDate = ""
    private var toDoPriority = Priority.Significant
    private var toDoType: ToDoType = ToDoType.You

    companion object {
        fun getImage(toDoType: ToDoType): Int {
            return when (toDoType) {
                ToDoType.Work -> R.drawable.work
                ToDoType.Study -> R.drawable.study
                ToDoType.Home -> R.drawable.home
                ToDoType.You -> R.drawable.you
                ToDoType.Administration -> R.drawable.administration
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myAdapter = MyArrayAdapter(this, list)
        toDoList.adapter = myAdapter
        deadlineTextView.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        toDoDate = deadlineTextView.text.toString()
        dateListener()
        priorityListener()
        deleteListener()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList("list", list)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        list = savedInstanceState!!.getParcelableArrayList("list")!!
        myAdapter = MyArrayAdapter(this, list)
        toDoList.adapter = myAdapter
        myAdapter!!.notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    private fun dateListener() {
        deadlineTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val dayString = "" + (if (day < 10) "0" else "") + day
                    val monthString = "" + (if (month < 9) "0" else "") + (month + 1)
                    deadlineTextView.text = "$dayString.$monthString.$year"
                    toDoDate = "$dayString.$monthString.$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }

    private fun priorityListener() {
        prioritySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val priorityString = when (prioritySeekBar.progress) {
                    1 -> "Inconsequential"
                    2 -> "Unimportant"
                    3 -> "Significant"
                    4 -> "Important"
                    else -> "Crucial"
                }
                priorityTextView.text = priorityString
                toDoPriority = Priority.valueOf(priorityString)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun deleteListener() {
        toDoList.setOnItemLongClickListener { _, _, index, _ ->
            list.removeAt(index)
            myAdapter!!.notifyDataSetChanged()
            Toast.makeText(this, "To-Do was deleted", Toast.LENGTH_LONG).show()
            true
        }
    }

    fun pickImage(view: View) {
        var selection = ""
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Pick type of To-Do:")
        builder.setSingleChoiceItems(typesString, -1) { _, which ->
            selection = typesString[which]
        }
        builder.setPositiveButton("Ok") { _, _ ->
            if (selection != "") {
                toDoType = ToDoType.valueOf(selection)
                pickImageButton.setImageResource(getImage(toDoType))
            }
        }
        builder.setNegativeButton("Cancel") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun addToDo(view: View) {
        if (newToDoEditText.text.toString() == "") {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Name")
            builder.setMessage("To-Do needs to have name.")
            builder.setPositiveButton("OK") { _, _ -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            return
        }
        val toDoTask = ToDoTask(
            newToDoEditText.text.toString(),
            SimpleDateFormat("dd.MM.yyy").parse(toDoDate),
            toDoPriority,
            toDoType
        )
        list.add(toDoTask)
        myAdapter!!.notifyDataSetChanged()
        Toast.makeText(this, "To-Do was added", Toast.LENGTH_LONG).show()
        for (i in 0 until list.size) {
            println(list[i])

        }
    }

    fun sortButton(view: View) {
        var selection = ""
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Pick order of sort:")
        builder.setSingleChoiceItems(ordersString, -1) { _, which ->
            selection = ordersString[which]
        }
        builder.setPositiveButton("Ok") { _, _ ->
            if (selection != "") {
                when (selection) {
                    "By Name" -> sortByName()
                    "By Priority" -> sortByPriority()
                    "By Type" -> sortByType()
                    "By Date" -> sortByDate()
                }
                println(selection)
                myAdapter!!.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancel") { _, _ ->
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun sortRadioGroup(view: View) {
        val id = radioGroup.checkedRadioButtonId
        if (id == -1) {
            Toast.makeText(this, "Check order of sorting", Toast.LENGTH_LONG).show()
            return
        }
        when (resources.getResourceEntryName(id)) {
            "sortByName" -> sortByName()
            "sortByPriority" -> sortByPriority()
            "sortByType" -> sortByType()
            "sortByDate" -> sortByDate()
        }
        myAdapter!!.notifyDataSetChanged()
    }

    private fun sortByName() {
        for (i in list.size - 1 downTo 0) {
            for (j in 0 until i) {
                if (list[j].name > list[j + 1].name) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                }
            }
        }
    }

    private fun sortByPriority() {
        for (i in list.size - 1 downTo 0) {
            for (j in 0 until i) {
                if (list[j].priority.getWeight() < list[j + 1].priority.getWeight()) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                }
            }
        }
    }

    private fun sortByType() {
        for (i in list.size - 1 downTo 0) {
            for (j in 0 until i) {
                if (list[j].type.getWeight() > list[j + 1].type.getWeight()) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                }
            }
        }
    }

    private fun sortByDate() {
        for (i in list.size - 1 downTo 0) {
            for (j in 0 until i) {
                if (list[j].deadline > list[j + 1].deadline) {
                    val tmp = list[j]
                    list[j] = list[j + 1]
                    list[j + 1] = tmp
                }
            }
        }
    }
}
