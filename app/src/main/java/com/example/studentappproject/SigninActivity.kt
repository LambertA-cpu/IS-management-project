package com.example.studentappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SigninActivity : AppCompatActivity() {
    private lateinit var StudentName: EditText
    private lateinit var StudentEmail: EditText
    private lateinit var StudentPhone: EditText
    private lateinit var StudentPass: EditText
    private lateinit var btn_save: Button
    private lateinit var dbref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_activity)

        StudentName = findViewById(R.id.stName)
        StudentEmail = findViewById(R.id.stEmail)
        StudentPhone = findViewById(R.id.stPhone)
        StudentPass = findViewById(R.id.stPassword)
        StudentPass = findViewById(R.id.stPassword2)
        btn_save = findViewById(R.id.btn_save)

        val textView: TextView = findViewById(R.id.textView2)

        //Connects to the db and initializes\creates the student table
        dbref = FirebaseDatabase.getInstance().getReference("Students")

        textView.setOnClickListener{
                val intent = Intent(this,LoginActivity::class. java)
                startActivity(intent)
        }

        //Saves Student Data to the db when the Register button is pressed
        btn_save.setOnClickListener {
            saveStudentData()
        }

    }
    //Getting value inserted from Edit text
    private fun saveStudentData(){
        val stName = StudentName.text.toString()
        val stEmail = StudentEmail.text.toString()
        val stPhone = StudentPhone.text.toString()
        val stPassword = StudentPass.text.toString()
        val stPassword2 = StudentPass.text.toString()

        //conditions when different errors occur
        if (stName.isEmpty()){
            StudentName.error = " Please enter your Name"
        }
        if (stEmail.isEmpty()){
            StudentEmail.error = " Please enter Email Address"
        }
        if (stPhone.isEmpty()){
            StudentPhone.error = " Please enter Your Phone Number"
        }
        if (stPassword.isEmpty()){
            StudentPass.error = " Please enter Password!"
        }
        if (stPassword2==stPassword){
            StudentPass.error = " Passwords do not Match! "
        }

        val stId = dbref.push().key!!

        val Students = StudentModel(stId, stName, stEmail, stPhone, stPassword)

        dbref.child(stId).setValue(Students)
            .addOnCompleteListener{
                Toast.makeText(this,"Data has been stored successfully!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error${err.message}", Toast.LENGTH_LONG).show()
            }
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

}