package com.app.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_new_element.*

class CreateNewExternalItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_element)

        this.title = "New title"

        addNewItemBtn.setOnClickListener {
            if (itemTitleTV.text.toString() == "" || itemTitleTV.text == null) {
                Toast.makeText(this, "Field should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val internalItemId = getRandomString(40)

            val item = ExternalItemsModel(
                intent.getStringExtra("Uid")!!,
                internalItemId,
                R.drawable.ic_assignment_black_24dp,
                itemTitleTV.text.toString()
            )

            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference("externalItemsModel")

            ref.push().setValue(item).addOnSuccessListener {
                Toast.makeText(this, "Title saved", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Titles::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }.addOnFailureListener {

                Toast.makeText(this, "Error: Title not saved", Toast.LENGTH_SHORT).show()
            }


        }

        }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}
