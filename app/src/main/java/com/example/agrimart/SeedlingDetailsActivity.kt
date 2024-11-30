package com.example.agrimart.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Seed
import com.example.agrimart.Model.Seedling
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivitySeedDetailsBinding
import com.example.agrimart.databinding.ActivitySeedlingBinding
import com.example.agrimart.databinding.ActivitySeedlingDetailsBinding
import com.example.agrimarttrader.Class.ControlImage

class SeedlingDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeedlingDetailsBinding
    lateinit var seedlingId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeedlingDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seedlingId = intent.getStringExtra("seedlingId").toString()


        setAllData()


        binding.back.setOnClickListener{
            finish()
        }

        binding.plus.setOnClickListener{
            var num = binding.quantity.text.toString().toInt()
            num++
            binding.quantity.text = num.toString()
        }


        binding.minus.setOnClickListener{
            var num = binding.quantity.text.toString().toInt()
            num--

            if(num<1){
                Toast.makeText(this, "Minimum 1 item required",Toast.LENGTH_SHORT).show()
            }else{
                binding.quantity.text = num.toString()
            }
        }




    }

    private fun setAllData() {

        MyClass().fetchSingleData("Seedling",seedlingId, Seedling::class.java, onDataChange = { seedling->
            if(seedling!=null){
                ControlImage(this, activityResultRegistry, "asdfadf" ).
                setImageByURl(seedling.pic.toString(),binding.pic)

                binding.name.text = seedling.name + " Seedlings"
                binding.rate.text = "৳"+seedling.rate+"/"
                binding.unit.text = seedling.unit
                binding.rating.text = seedling.rating
                binding.description.text = seedling.description







            }
        }, onCancelled = {

        })


    }
}