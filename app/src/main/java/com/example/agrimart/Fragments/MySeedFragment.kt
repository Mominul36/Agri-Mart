package com.example.agrimart.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrimart.Activity.AddSeedActivity
import com.example.agrimart.Adapters.MySeedAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.FragmentMySeedBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MySeedFragment : Fragment() {
    private lateinit var binding: FragmentMySeedBinding
    lateinit var seedList : MutableList<Seed>



    lateinit var adapter: MySeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMySeedBinding.inflate(inflater, container, false)

        seedList = mutableListOf()
        adapter = MySeedAdapter(requireContext(),seedList)
        binding.recyclerViewFertilizers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFertilizers.adapter = adapter

        binding.recyclerViewFertilizers.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE




        fetchFertilizer()


        binding.fabAddFertilizer.setOnClickListener {

            startActivity(Intent(requireContext(),AddSeedActivity::class.java))


        }

        return binding.root
    }

    private fun fetchFertilizer() {

        binding.recyclerViewFertilizers.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        MyClass().getCurrentFarmer { farmer->
            if(farmer!=null){
                var farmerId = farmer.id.toString()


                var database = FirebaseDatabase.getInstance().getReference("Seed")

                database.orderByChild("farmerId").equalTo(farmerId).addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(snapshot.exists()){
                            seedList.clear()

                            for(childSnapshot in snapshot.children){
                                var fertilizer = childSnapshot.getValue(Seed::class.java)
                                if(fertilizer!=null){
                                    seedList.add(fertilizer)
                                }

                            }



                            binding.recyclerViewFertilizers.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            adapter.notifyDataSetChanged()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })


            }

        }




    }
}
