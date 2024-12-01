package com.example.agrimart.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.agrimart.Fragments.BoundCopyFragment
import com.example.agrimart.Fragments.MaizeFragment
import com.example.agrimart.Fragments.PotatoFragment
import com.example.agrimart.Fragments.WheatFragment
import com.example.agrimart.R
import com.example.agrimart.databinding.ActivityCultivationPrcessBinding

class CultivationPrcessActivity : AppCompatActivity() {

    lateinit var binding: ActivityCultivationPrcessBinding
    var flag: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCultivationPrcessBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.maize.setOnClickListener{
            setFragment(MaizeFragment())
        }


        binding.wheat.setOnClickListener{
            setFragment(WheatFragment())
        }

        binding.bound.setOnClickListener{
            setFragment(BoundCopyFragment())
        }

        binding.potato.setOnClickListener{
            setFragment(PotatoFragment())
        }















    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun setFragment(fragment: Fragment){
        val fragmentManager : FragmentManager = supportFragmentManager
        val frammentTransition : FragmentTransaction = fragmentManager.beginTransaction()

        if(!flag){
            frammentTransition.add(R.id.frame,fragment)
            flag = true
        }
        else{
            frammentTransition.replace(R.id.frame,fragment)
        }
        frammentTransition.addToBackStack(null)
        frammentTransition.commit()
    }


}