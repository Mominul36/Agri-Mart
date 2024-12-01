package com.example.agrimart.Activity



import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.agrimart.Adapters.PesticideAdapter
import com.example.agrimart.Adapters.SeedAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Fertilizer
import com.example.agrimart.Model.Pesticide
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivityFertilizerBinding
import com.example.agrimart.databinding.ActivityPesticideBinding
import com.example.agrimart.databinding.ActivitySeedBinding

class PesticideActivity : AppCompatActivity() {
    lateinit var binding: ActivityPesticideBinding

    lateinit var pesticideList : MutableList<Pesticide>
    lateinit var adapter : PesticideAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPesticideBinding.inflate(layoutInflater)
        setContentView(binding.root)



        pesticideList = mutableListOf()
        adapter = PesticideAdapter(this,pesticideList)
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
            MyClass().fetchAllData("Pesticide", onDataChange = { snapshot->
                if(snapshot!=null){

                    pesticideList.clear()
                    for(childrenSnapshot in snapshot.children){
                        var seed = childrenSnapshot.getValue(Pesticide::class.java)
                        if(seed!=null){
                            if((seed.name.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.division.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.district.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        seed.thana.toString().toLowerCase().contains(text.toLowerCase()))){
                                pesticideList.add(seed)
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