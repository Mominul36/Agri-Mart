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
import com.example.agrimart.Adapters.FertilizerAdapter
import com.example.agrimart.Adapters.SeedAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Fertilizer
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivityFertilizerBinding
import com.example.agrimart.databinding.ActivitySeedBinding

class FertilizerActivity : AppCompatActivity() {
    lateinit var binding: ActivityFertilizerBinding

    lateinit var fertilizerList : MutableList<Fertilizer>
    lateinit var adapter : FertilizerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFertilizerBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fertilizerList = mutableListOf()
        adapter = FertilizerAdapter(this,fertilizerList)
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
            MyClass().fetchAllData("Fertilizer", onDataChange = { snapshot->
                if(snapshot!=null){

                    fertilizerList.clear()
                    for(childrenSnapshot in snapshot.children){
                        var seed = childrenSnapshot.getValue(Fertilizer::class.java)
                        if(seed!=null){
                            if((seed.name.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.division.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.district.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.thana.toString().toLowerCase().contains(text.toLowerCase()))){
                                fertilizerList.add(seed)
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