package com.example.agrimart.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimart.Adapters.MyProductPagerAdapter
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivityMyProductBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyProductPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Crop"
                1 -> "Seed"
                2 -> "Seedling"
                else -> "Crops"
            }
        }.attach()




        binding.back.setOnClickListener{
            finish()
        }


    }
}
