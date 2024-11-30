package com.example.agrimart

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
import com.example.agrimart.Adapters.SeedlingAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Seed
import com.example.agrimart.Model.Seedling
import com.example.agrimart.databinding.ActivitySeedBinding
import com.example.agrimart.databinding.ActivitySeedlingBinding

class SeedlingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeedlingBinding

    lateinit var seedlingList : MutableList<Seedling>
    lateinit var adapter : SeedlingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeedlingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        seedlingList = mutableListOf()
        adapter = SeedlingAdapter(this,seedlingList)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        binding.recyclerView.adapter = adapter


        fetchAllSeedling()



        binding.search.addTextChangedListener { editable ->
            fetchAllSeedling()
        }



        binding.back.setOnClickListener{
            finish()
        }


    }

    private fun fetchAllSeedling() {
        var text: String = binding.search.text.toString()

        MyClass().getCurrentFarmer { farmer->
            MyClass().fetchAllData("Seedling", onDataChange = { snapshot->
                if(snapshot!=null){

                    seedlingList.clear()
                    for(childrenSnapshot in snapshot.children){
                        var seedling = childrenSnapshot.getValue(Seedling::class.java)
                        if(seedling!=null){
                            if((seedling.name.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seedling.division.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seedling.district.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seedling.thana.toString().toLowerCase().contains(text.toLowerCase())) && seedling.farmerId!= farmer!!.id){
                                seedlingList.add(seedling)
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