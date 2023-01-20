package com.example.studentappproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import com.example.studentappproject.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    //private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    //firebase database code...
        firebaseDatabase = FirebaseDatabase.getInstance()
        //dbref = FirebaseDatabase.getInstance().getReference("Tasks").child(tkId)

        val add: Button = findViewById(R.id.btnAdd)
        add.setOnClickListener {
            val intent = Intent(this, CreateCard::class.java)
            startActivity(intent)
        }
        val deleteAll: Button = findViewById(R.id.deleteAll)
        deleteAll.setOnClickListener {
            DataObject.deleteAll()



            setRecycler()
        }

        setRecycler()

    }

    fun setRecycler() {
        recycler_view.adapter = Adapter(DataObject.getAllData())
        recycler_view.layoutManager = LinearLayoutManager(this)
    }


}
