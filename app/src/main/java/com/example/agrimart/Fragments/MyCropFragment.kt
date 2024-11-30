package com.example.agrimart.Fragments

import com.example.agrimart.Activity.AddCropActivity
import com.example.agrimart.Adapters.MyCropAdapter
import com.example.agrimart.Model.Crop
import com.example.agrimart.databinding.FragmentMyCropBinding
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.agrimart.Class.MyClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MyCropFragment : Fragment() {
    private lateinit var binding: FragmentMyCropBinding
    lateinit var cropList : MutableList<Crop>



    lateinit var adapter: MyCropAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCropBinding.inflate(inflater, container, false)

        cropList = mutableListOf()
        adapter = MyCropAdapter(requireContext(),cropList)
        binding.recyclerViewFertilizers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFertilizers.adapter = adapter

        binding.recyclerViewFertilizers.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE




        fetchFertilizer()


        binding.fabAddFertilizer.setOnClickListener {

            startActivity(Intent(requireContext(), AddCropActivity::class.java))


        }

        return binding.root
    }

    private fun fetchFertilizer() {

        binding.recyclerViewFertilizers.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE

        MyClass().getCurrentFarmer { farmer->
            if(farmer!=null){
                var farmerId = farmer.id.toString()


                var database = FirebaseDatabase.getInstance().getReference("Crop")

                database.orderByChild("farmerId").equalTo(farmerId).addValueEventListener(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(snapshot.exists()){
                            cropList.clear()

                            for(childSnapshot in snapshot.children){
                                var crop = childSnapshot.getValue(Crop::class.java)
                                if(crop!=null){
                                    cropList.add(crop)
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
