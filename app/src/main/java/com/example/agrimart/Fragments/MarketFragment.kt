package com.example.agrimart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrimart.Activity.CropActivity
import com.example.agrimart.R
import com.example.agrimart.SeedActivity
import com.example.agrimart.SeedlingActivity
import com.example.agrimart.databinding.FragmentMarketBinding


class MarketFragment : Fragment() {
    lateinit var binding: FragmentMarketBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)


        binding.seed.setOnClickListener{
            startActivity(Intent(requireContext(),SeedActivity::class.java))
        }

        binding.seedling.setOnClickListener{
            startActivity(Intent(requireContext(),SeedlingActivity::class.java))
        }


        binding.cereal.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Cereal")
            startActivity(intent)
        }

        binding.pulse.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Pulse")
            startActivity(intent)
        }

        binding.fruit.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Fruit")
            startActivity(intent)
        }


        binding.vegetable.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Vegetable")
            startActivity(intent)
        }
        binding.fiber.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Fiber")
            startActivity(intent)
        }

        binding.beverage.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Beverage")
            startActivity(intent)
        }

        binding.medicinal.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Medicinal")
            startActivity(intent)
        }

        binding.spice.setOnClickListener{
            var intent = Intent(requireContext(),CropActivity::class.java)
            intent.putExtra("category","Spice")
            startActivity(intent)
        }






        return binding.root
    }


}