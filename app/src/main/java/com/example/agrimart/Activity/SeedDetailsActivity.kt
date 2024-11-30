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
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivitySeedDetailsBinding
import com.example.agrimarttrader.Class.ControlImage

class SeedDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeedDetailsBinding
    lateinit var seedId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySeedDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seedId = intent.getStringExtra("seedId").toString()


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

        MyClass().fetchSingleData("Seed",seedId, Seed::class.java, onDataChange = { seed->
            if(seed!=null){
              ControlImage(this, activityResultRegistry, "asdfadf" ).
                      setImageByURl(seed.pic.toString(),binding.pic)

                binding.name.text = seed.name + " Seeds"
                binding.rate.text = "৳"+seed.rate+"/"
                binding.unit.text = seed.unit
                binding.rating.text = seed.rating
                binding.description.text = seed.description







            }
        }, onCancelled = {

        })


    }
}