package com.app.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.Models.InternalItemsModel
import com.app.myapplication.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_new_element.*
import kotlinx.android.synthetic.main.activity_create_new_internal_item.*

class CreateNewInternalItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_internal_item)

        this.title = "New task"

        addNewInternalItemBtn.setOnClickListener {
            if (itemTimeTV.text.toString() == "" || itemTimeTV.text == null || itemContentTV.text.toString() == "" || itemContentTV.text == null) {
                Toast.makeText(this, "Fields should not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = InternalItemsModel(
                itemContentTV.text.toString(),
                Firebase.auth.currentUser!!.uid,
                itemTimeTV.text.toString(),
                intent.getStringExtra("itemId")!!
            )

            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference("internalItemsModel")

            ref.push().setValue(item).addOnSuccessListener {
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Diary::class.java)
                    .apply { putExtra("diaryTitle", intent.getStringExtra("diaryTitle"))}
                    .apply { putExtra("itemId", intent.getStringExtra("itemId")!!)}
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }.addOnFailureListener {
                Toast.makeText(this, "Error: Note not saved", Toast.LENGTH_SHORT).show()
            }

        }
    }
}
