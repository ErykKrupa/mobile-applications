package com.example.simpletodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_search.*

class SearchDialogFragment(var activity: MainActivity) : DialogFragment() {
    override fun onStart() {
        super.onStart()
        cancelButton.setOnClickListener { dialog.dismiss() }
        searchButton.setOnClickListener {
            activity.onSearch(editText.text.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_search, container, false)
    }
}