package com.example.agrimart.Adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.agrimart.Fragments.MyCropFragment
import com.example.agrimart.Fragments.MySeedFragment
import com.example.agrimart.Fragments.MySeedlingFragment

class MyProductPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyCropFragment()
            1 -> MySeedFragment()
            2 -> MySeedlingFragment()
            else -> MyCropFragment()
        }
    }
}
