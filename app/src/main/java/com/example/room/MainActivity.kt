package com.example.room

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class MainActivity : AppCompatActivity(), CustomAdapter.NoteClickListener {
    private lateinit var viewModel: ContactViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var surnameET: EditText
    private lateinit var nameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveButton: Button
    private lateinit var recyclerView: RecyclerView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun init(){
        toolbar = findViewById<Toolbar?>(R.id.toolbar).apply {
            title = "Записная книжка"
            setNavigationIcon(R.drawable.ic_exit)
            setNavigationOnClickListener {
                finish()
            }
        }

        surnameET= findViewById(R.id.surnameET)
        nameET= findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)

        saveButton = findViewById(R.id.saveButton)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(this, this)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(application))[ContactViewModel::class.java]
        viewModel.contacts.observe(this){list ->
            list?.let {
                adapter.updateList(it)
            }
        }
        saveButton.setOnClickListener {
            saveContact()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun saveContact(){
        val surname = surnameET.text.toString()
        val name = nameET.text.toString()
        val phone = phoneET.text.toString()
        val time = formatMilliseconds(Date().time)
        if (surname.isNotEmpty() && phone.isNotEmpty()){
            viewModel.insertContact(Contact(surname, name, phone, time))
            Toast.makeText(this, "Контакт записан", Toast.LENGTH_LONG).show()
        }
        surnameET.text.clear()
        phoneET.text.clear()
        nameET.text.clear()
    }

        @SuppressLint("SimpleDateFormat")
        @RequiresApi(Build.VERSION_CODES.N)
        private fun formatMilliseconds(time: Long): String {
        val timeFormat = SimpleDateFormat("EEE, HH:mm")
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+5")
        return timeFormat.format(Date(time))
    }

    override fun onItemClicked(contact: Contact) {
        viewModel.deleteContact(contact)
        Toast.makeText(this, "Контакт удален", Toast.LENGTH_LONG).show()
    }

}