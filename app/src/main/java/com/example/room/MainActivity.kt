package com.example.room

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactViewModel
    private lateinit var toolbar: Toolbar
    private lateinit var surnameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveButton: Button
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        toolbar = findViewById<Toolbar?>(R.id.toolbar).apply {
            title = "Записная книжка"
            setNavigationIcon(R.drawable.ic_exit)
            setNavigationOnClickListener {
                finish()
            }
        }

        surnameET= findViewById(R.id.surnameET)
        phoneET = findViewById(R.id.phoneET)

        saveButton = findViewById(R.id.saveButton)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CustomAdapter(this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(application))[ContactViewModel::class.java]
        viewModel.contacts.observe(this){list ->
            list?.let {
                adapter.updateList(it)
            }
        }
        saveButton.setOnClickListener {
            saveContact(viewModel)
        }
    }

    private fun saveContact(view: ContactViewModel){
        val surname = surnameET.text.toString()
        val phone = phoneET.text.toString()
        if (surname.isNotEmpty() && phone.isNotEmpty()){
            viewModel.insertContact(Contact(surname, phone))
            Toast.makeText(this, "Контакт записан", Toast.LENGTH_LONG).show()
        }
        surnameET.text.clear()
        phoneET.text.clear()
    }

}