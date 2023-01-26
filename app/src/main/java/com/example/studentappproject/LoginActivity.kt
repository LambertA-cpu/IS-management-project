package com.example.studentappproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref: DatabaseReference
    private lateinit var StudentEmail: EditText
    private lateinit var StudentPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        StudentEmail = findViewById(R.id.stEmail)
        StudentPass = findViewById(R.id.stPassword)

        dbref = FirebaseDatabase.getInstance().getReference("Students")

        val textView: TextView = findViewById(R.id.textView1)
        val btnLogin: Button = findViewById(R.id.btn_Login)

        textView.setOnClickListener{
            val intent = Intent(this,SigninActivity::class. java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener{
            checkStudentData()
        }

    }

    private fun checkStudentData() {
        //Getting user input
        val stEmail = StudentEmail.text.toString()
        val stPassword = StudentPass.text.toString()

        //null checks on inputs
        if (stEmail.isEmpty()){
            StudentEmail.error = " Please enter Email Address"
        }
        if (stPassword.isEmpty()){
            StudentPass.error = " Please enter Password!"
        }
        auth.signInWithEmailAndPassword(stEmail,stPassword)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    //Move to main activity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext,"Authentication failed!",Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(baseContext,"Authentication failed!${it.localizedMessage}",Toast.LENGTH_SHORT)
                    .show()
            }

    }
}