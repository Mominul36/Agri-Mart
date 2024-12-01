package com.example.agrimart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.agrimart.Activity.CultivationPrcessActivity
import com.example.agrimart.Activity.WeatherActivity
import com.example.agrimart.Activity.WorkerActivity
import com.example.agrimart.R
import com.example.agrimart.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {
    lateinit var binding: FragmentServiceBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceBinding.inflate(inflater, container, false)

        binding.cultivation.setOnClickListener{
            startActivity(Intent(requireContext(),CultivationPrcessActivity::class.java))
        }


        binding.weather.setOnClickListener{
            startActivity(Intent(requireContext(),WeatherActivity::class.java))
        }



        binding.worker.setOnClickListener{
            startActivity(Intent(requireContext(),WorkerActivity::class.java))
        }



















        return binding.root


    }


}