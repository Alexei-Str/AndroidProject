package com.app.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.app.myapplication.Activity.Titles
import com.app.myapplication.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthManager {

    private lateinit var database: DatabaseReference


    fun userLogin(activity: FragmentActivity?, context: Context?, email: String, password: String) {

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                val intent = Intent(activity, Titles::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (context != null) {
                    startActivity(context, intent, null)
                }

            }
            .addOnFailureListener {
                Log.d("Main", "Failed to signIn user: ${it.message}")
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
    }


    fun registration(activity: FragmentActivity?, context: Context?, name: String, email: String, password: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                Toast.makeText(context, "Registration completed successfully", Toast.LENGTH_SHORT).show()

                saveUserToFirebaseDB(activity, context, name, email)

            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun saveUserToFirebaseDB(activity: FragmentActivity?, context: Context?, name: String, email: String) {

        database = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val user = User(uid, name, email)

        database.child("users").child(uid).setValue(user)
            .addOnCompleteListener {

                val intent = Intent(activity, Titles::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (context != null) {
                    startActivity(context, intent, null)
                }
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to upload user: ${it.message}")
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
    }
}