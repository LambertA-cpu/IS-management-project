package com.example.studentappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_update_card.*
import kotlinx.android.synthetic.main.activity_update_card.create_Details
import kotlinx.android.synthetic.main.activity_update_card.create_priority
import kotlinx.android.synthetic.main.activity_update_card.create_title


class UpdateCard : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_card)
        database = FirebaseDatabase.getInstance().getReference("Tasks")
        //database = Room.databaseBuilder(
        //applicationContext, myDatabase::class.java, "To_Do"
        //).build()
        val pos = intent.getIntExtra("id", -1)
        if (pos != -1) {
            val title = DataObject.getData(pos).title
            val priority = DataObject.getData(pos).priority
            val details: String = DataObject.getData(pos).details
            create_title.setText(title)
            create_priority.setText(priority)
            create_Details.setText(details)

            delete_button.setOnClickListener {
                DataObject.deleteData(pos)
                database.removeValue()
                //GlobalScope.launch {
                //database.dao().deleteTask(
                /*Entity(
                            pos + 1,
                            create_title.text.toString(),
                            create_priority.text.toString()
                        )
                    )*/
                //}
                myIntent()
            }

            update_button.setOnClickListener {
                DataObject.updateData(
                    pos,
                    create_title.text.toString(),
                    create_priority.text.toString(),
                    create_Details.text.toString()
                )
                database.setValue(title,priority)
                /*GlobalScope.launch {
                    database.dao().updateTask(
                        Entity(
                            pos + 1, create_title.text.toString(),
                            create_priority.text.toString()
                        )
                    )*/
                //}
                myIntent()
            }

        }
    }

    private fun myIntent() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}