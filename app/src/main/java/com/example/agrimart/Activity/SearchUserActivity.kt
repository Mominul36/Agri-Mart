package com.example.agrimart.Activity


import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrimart.Adapters.UserAdapter
import com.example.agrimart.Model.Farmer

import com.example.agrimart.databinding.ActivitySearchUserBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchUserActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchUserBinding
    lateinit var userAdapter: UserAdapter
    lateinit var userList: MutableList<Farmer>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.search.requestFocus()

        userList = mutableListOf()
        userAdapter = UserAdapter(this,userList)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = userAdapter


        val databaseReference = FirebaseDatabase.getInstance().getReference("Farmer")




        binding.search.addTextChangedListener { editable ->
            val queryText = editable.toString().trim()

            if (queryText.isNotEmpty()) {
                fetchUsersFromFirebase(queryText, databaseReference)
            } else {
                userList.clear()
                userAdapter.notifyDataSetChanged()
            }
        }

        binding.back.setOnClickListener{
            finish()
        }








    }





    private fun fetchUsersFromFirebase(queryText: String, databaseReference: DatabaseReference) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(Farmer::class.java)
                    user?.let {
                        if(user.name!!.contains(queryText) || user.phone!!.contains(queryText)){
                            userList.add(it)
                        }
                    }
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("SearchUserActivity", "Error fetching users: ${error.message}")
            }
        })
    }


}