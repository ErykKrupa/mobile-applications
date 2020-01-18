package com.example.simpletodo

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var dao: TaskDao
    private var data: LiveData<List<Task>>? = null

    fun onSearch(word: String) {
        dao = TaskDatabase.getInstance(this).TaskDao()
        val taskAdapter = TaskAdapter(this, ArrayList())

        AsyncTask.execute {
            data = dao.getSome("%$word%")
            runOnUiThread {
                data!!.observe(
                    this,
                    androidx.lifecycle.Observer { t -> taskAdapter.clear();taskAdapter.addAll(t); })
            }
        }
        taskListView.adapter = taskAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        dao = TaskDatabase.getInstance(this).TaskDao()
        val taskAdapter = TaskAdapter(this, ArrayList())
        AsyncTask.execute {
            data = dao.getAll()
            runOnUiThread {
                data!!.observe(this, androidx.lifecycle.Observer { t -> taskAdapter.clear(); taskAdapter.addAll(t); })
            }
        }
        taskListView.adapter = taskAdapter
        taskListView.setOnItemLongClickListener { parent, view, position, id ->
            Log.d("Delete", "Delete $position")
            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete this task?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { dialog, which ->
                    AsyncTask.execute {
                        dao.delete(
                            parent.getItemAtPosition(
                                position
                            ) as Task
                        )
                    }
                }
                .show()
            true
        }
        fab.setOnClickListener {
            NewTaskDialogFragment().show(supportFragmentManager, "New Task")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return when (item.itemId) {
            R.id.action_populate -> {
                populate()
                (taskListView.adapter as TaskAdapter).notifyDataSetChanged()
                true
            }
            R.id.action_clear_done -> {
                AsyncTask.execute { dao.deleteDone() }
                true
            }
            R.id.action_clear_all -> {
                AsyncTask.execute { dao.deleteAll() }
                true
            }
            R.id.action_search -> {
                SearchDialogFragment(this).show(supportFragmentManager, "Search")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun populate() {
        val arrayList = arrayListOf<Task>()
        arrayList.add(Task("test1", Calendar.getInstance().time, 0))
        arrayList.add(Task("test2", Calendar.getInstance().time, 1))
        arrayList.add(Task("test3", Calendar.getInstance().time, 2))
        arrayList.add(Task("test1", Calendar.getInstance().time, 3))
        arrayList.add(Task("test2", Calendar.getInstance().time, 4))
        arrayList.add(Task("test3", Calendar.getInstance().time, 5))
        arrayList.add(Task("test1", Calendar.getInstance().time, 6))
        arrayList.add(Task("test2", Calendar.getInstance().time, 7))
        arrayList.add(Task("test3", Calendar.getInstance().time, 0))
        arrayList.add(Task("test1", Calendar.getInstance().time, 0))
        arrayList.add(Task("test2", Calendar.getInstance().time, 0))
        arrayList.add(Task("test3", Calendar.getInstance().time, 0))
        arrayList.add(Task("test1", Calendar.getInstance().time, 0))
        arrayList.add(Task("test2", Calendar.getInstance().time, 0))
        arrayList.add(Task("test3", Calendar.getInstance().time, 0))
        AsyncTask.execute { dao.insertAll(arrayList) }
    }
}
