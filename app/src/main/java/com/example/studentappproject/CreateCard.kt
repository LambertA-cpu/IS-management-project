package com.example.studentappproject


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.studentappproject.databinding.ActivityCreateCardBinding
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_card.*
import java.util.*

class CreateCard : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCardBinding
    private lateinit var database: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_card)

            binding = ActivityCreateCardBinding.inflate(layoutInflater)
            setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        binding.saveButton.setOnClickListener { scheduleNotification() }
        //FIREBASE DB CONNECTION...
        database = FirebaseDatabase.getInstance().getReference("Student").child("StudentId").child("Tasks")
        //database = Firebase.database.reference
        save_button.setOnClickListener {
            if (save_button.text.toString().trim { it <= ' ' }.isNotEmpty()
                && create_priority.text.toString().trim { it <= ' ' }.isNotEmpty()
            ) {
                val title = create_title.text.toString()
                val priority = create_priority.text.toString()
                val details = create_Details.text.toString()
                DataObject.setData(title, priority, details)

                val tkId = database.push().key!!

                val task = CardInfo(title, priority, details)

                database.child(tkId).setValue(task)
                    .addOnCompleteListener {
                        Toast.makeText(
                            this,
                            "Data has been stored successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    }.addOnFailureListener { err ->
                        Toast.makeText(this, "Error${err.message}", Toast.LENGTH_LONG).show()
                    }

            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification()
    {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = binding.createTitle.text.toString()
        val message = binding.createDetails.text.toString()
        intent.putExtra(createTitle, title)
        intent.putExtra(createDetails, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent
            )
        }
        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long
    {
        val minute = binding.timePicker.minute
        val hour = binding.timePicker.hour
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month
        val year = binding.datePicker.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel.description = desc
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

