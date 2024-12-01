package com.example.agrimart.Activity


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.agrimart.Adapters.SeedAdapter
import com.example.agrimart.Adapters.WorkerAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Farmer
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivitySeedBinding
import com.example.agrimart.databinding.ActivityWorkerBinding

class WorkerActivity : AppCompatActivity() {
    lateinit var binding: ActivityWorkerBinding

    lateinit var farmerList : MutableList<Farmer>
    lateinit var adapter : WorkerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWorkerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        farmerList = mutableListOf()
        adapter = WorkerAdapter(this,farmerList)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        binding.recyclerView.adapter = adapter


        fetchAllSeed()



        binding.search.addTextChangedListener { editable ->
            fetchAllSeed()
        }



        binding.back.setOnClickListener{
            finish()
        }


    }

    private fun fetchAllSeed() {
        var text: String = binding.search.text.toString()

        MyClass().getCurrentFarmer { farmer->
            MyClass().fetchAllData("Farmer", onDataChange = { snapshot->
                if(snapshot!=null){

                    farmerList.clear()
                    for(childrenSnapshot in snapshot.children){
                        var farmer = childrenSnapshot.getValue(Farmer::class.java)
                        if(farmer!=null){
                            if((farmer.name.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        farmer.division.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        farmer.district.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        farmer.thana.toString().toLowerCase().contains(text.toLowerCase())) && farmer.worker == true){
                                farmerList.add(farmer)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            }, onCancelled = {
            })


        }






    }
}