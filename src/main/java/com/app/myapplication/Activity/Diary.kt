package com.app.myapplication.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.myapplication.Adapters.InternalItemsAdapter
import com.app.myapplication.Models.InternalItemsModel
import com.app.myapplication.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_item_list.*
import java.util.ArrayList

class Diary : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        this.title = intent.getStringExtra("diaryTitle")

        fillDiary()
    }

    private fun fillDiary() {

        ref = FirebaseDatabase.getInstance().getReference("internalItemsModel")

        val diary: ArrayList<InternalItemsModel> = ArrayList<InternalItemsModel>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    if (item.getValue(InternalItemsModel::class.java)!!.internalItemId == intent.getStringExtra("itemId")!! &&
                        item.getValue(InternalItemsModel::class.java)!!.userId == Firebase.auth.currentUser?.uid
                    ) {
                        diary.add(item.getValue(InternalItemsModel::class.java)!!)
                        list.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })

        diary.add(
            InternalItemsModel(
                "Add new note",
                Firebase.auth.currentUser?.uid!!,
                "+",
                intent.getStringExtra("itemId")!!
            )
        )

        list.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = InternalItemsAdapter(diary) {

                if (it.data == "+"  && it.itemContent == "Add new note") {
                    startActivity(
                        Intent(this.context, CreateNewInternalItems::class.java)
                            .apply { putExtra("itemId", it.internalItemId) }
                            .apply { putExtra("diaryTitle", intent.getStringExtra("diaryTitle")) })


                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Titles::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        list.adapter?.notifyDataSetChanged()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}
