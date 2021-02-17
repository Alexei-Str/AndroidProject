package com.app.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.myapplication.Adapters.ExternalItemsAdapter
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_item_list.*
import java.util.ArrayList


class Titles : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        if (Firebase.auth.currentUser == null) {
            val intent = Intent(this, AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        this.title = Firebase.auth.currentUser?.displayName ?: ""

        fillTitleList()
    }

    private fun fillTitleList(){

            ref = FirebaseDatabase.getInstance().getReference("externalItemsModel")

            val listTitle: ArrayList<ExternalItemsModel> = ArrayList<ExternalItemsModel>()

            ref.addValueEventListener(object: ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    for(item in snapshot.children){
                        if(item.getValue(ExternalItemsModel::class.java)!!.userId == Firebase.auth.currentUser?.uid){
                            listTitle.add(item.getValue(ExternalItemsModel::class.java)!!)
                            list.adapter?.notifyDataSetChanged()
                        }
                    }
                }
            })

        listTitle.add(ExternalItemsModel(Firebase.auth.currentUser?.uid.toString(), "",  R.drawable.ic_add_black_24dp,"Add new table"))


        list.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ExternalItemsAdapter(listTitle) {

                if (it.itemPic == R.drawable.ic_add_black_24dp) {
                    startActivity(Intent(this.context, CreateNewExternalItems::class.java).apply{putExtra("Uid", Firebase.auth.currentUser?.uid)})
                } else {
                    val intent = Intent(this.context, Diary::class.java)
                        .apply{putExtra("itemId", it.internalItemId)}
                            .apply {putExtra("diaryTitle", it.title)}
                    startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        list.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.logout -> {
                Firebase.auth.signOut()
                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            R.id.deleteall -> {
//                val ref = FirebaseDatabase.getInstance().getReference("externalItemsModel")
//                list.adapter?.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}



