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
import com.example.agrimart.Adapters.CropAdapter
import com.example.agrimart.Adapters.SeedAdapter
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Crop
import com.example.agrimart.Model.Seed
import com.example.agrimart.databinding.ActivityCropBinding
import com.example.agrimart.databinding.ActivitySeedBinding

class CropActivity : AppCompatActivity() {
    lateinit var binding: ActivityCropBinding

    lateinit var cropList : MutableList<Crop>
    lateinit var adapter : CropAdapter
    lateinit var category : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        category = intent.getStringExtra("category").toString()

        binding.cropName.text = category


        cropList = mutableListOf()
        adapter = CropAdapter(this,cropList)
        binding.recyclerView.layoutManager = GridLayoutManager(this,2)

        binding.recyclerView.adapter = adapter


        fetchAllCrop()



        binding.search.addTextChangedListener { editable ->
            fetchAllCrop()
        }



        binding.back.setOnClickListener{
            finish()
        }


    }

    private fun fetchAllCrop() {
        var text: String = binding.search.text.toString()

        MyClass().getCurrentFarmer { farmer->
            MyClass().fetchAllData("Crop", onDataChange = { snapshot->
                if(snapshot!=null){

                    cropList.clear()
                    for(childrenSnapshot in snapshot.children){
                        var crop = childrenSnapshot.getValue(Crop::class.java)
                        if(crop!=null){
                            if((crop.name.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        crop.division.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        crop.district.toString().toLowerCase().contains(text.toLowerCase()) ||
                                        crop.thana.toString().toLowerCase().contains(text.toLowerCase()) ) && crop.farmerId!= farmer!!.id
                                        && crop.category.toString()==category){
                                cropList.add(crop)
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