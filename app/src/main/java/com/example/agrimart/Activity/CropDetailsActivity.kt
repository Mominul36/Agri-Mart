package com.example.agrimart.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultRegistry
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimart.Class.MyClass
import com.example.agrimart.Model.Crop
import com.example.agrimart.Model.Seed
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivityCropDetailsBinding
import com.example.agrimart.databinding.ActivitySeedDetailsBinding
import com.example.agrimarttrader.Class.ControlImage

class CropDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCropDetailsBinding
    lateinit var cropId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCropDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cropId = intent.getStringExtra("cropId").toString()


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

        MyClass().fetchSingleData("Crop",cropId, Crop::class.java, onDataChange = { crop->
            if(crop!=null){
                ControlImage(this, activityResultRegistry, "asdfadf" ).
                setImageByURl(crop.pic.toString(),binding.pic)

                binding.name.text = crop.name
                binding.rate.text = "৳"+crop.rate+"/"
                binding.unit.text = crop.unit
                binding.rating.text = crop.rating
                binding.description.text = crop.description







            }
        }, onCancelled = {

        })


    }
}